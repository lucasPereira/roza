package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public final class MergeCandidateQueue {

	private static final int STALE_CANDIDATE_RATIO = 2;
	private static final int STALE_POLL_COMPACT_THRESHOLD = 10_000;

	private final ClusterLinkage linkage;
	private final TestCaseSimilarityMatrix matrix;
	private final StageProgress progress;
	private final Set<TestCaseCluster> activeClusters;
	private final Map<TestCaseCluster, Map<TestCaseCluster, Double>> similarities;
	private PriorityQueue<QueuedCandidate> candidates;
	private final int totalWork;
	private int completedMerges;

	public MergeCandidateQueue(List<TestCaseCluster> clusters, ClusterLinkage linkage, TestCaseSimilarityMatrix matrix) {
		this(clusters, linkage, matrix, StageProgress.ignore());
	}

	public MergeCandidateQueue(
			List<TestCaseCluster> clusters,
			ClusterLinkage linkage,
			TestCaseSimilarityMatrix matrix,
			StageProgress progress) {
		this.linkage = Objects.requireNonNull(linkage);
		this.matrix = Objects.requireNonNull(matrix);
		this.progress = progress == null ? StageProgress.ignore() : progress;
		this.activeClusters = Collections.newSetFromMap(new IdentityHashMap<>());
		this.activeClusters.addAll(Objects.requireNonNull(clusters));
		this.similarities = new IdentityHashMap<>();
		this.candidates = new PriorityQueue<>();
		int clusterCount = clusters.size();
		this.totalWork = Math.max(1, clusterCount - 1);
		this.progress.report(0, totalWork);
		addInitialCandidates(clusters);
		if (clusterCount <= 1) {
			this.progress.report(totalWork, totalWork);
		}
	}

	public List<MergeCandidate> bestCandidates() {
		compactIfNeeded();
		List<QueuedCandidate> tied = new ArrayList<>();
		Double best = null;
		int skippedStale = 0;
		while (!candidates.isEmpty()) {
			QueuedCandidate candidate = candidates.poll();
			if (!candidate.isActive(activeClusters)) {
				skippedStale++;
				if (skippedStale >= STALE_POLL_COMPACT_THRESHOLD) {
					compactIfNeeded();
					skippedStale = 0;
				}
				continue;
			}
			if (best == null) {
				best = candidate.similarity;
				tied.add(candidate);
			} else if (Double.compare(candidate.similarity, best) == 0) {
				tied.add(candidate);
			} else {
				candidates.add(candidate);
				break;
			}
		}
		if (tied.isEmpty()) {
			throw new IllegalStateException("No remaining merge candidate.");
		}
		List<MergeCandidate> resolved = new ArrayList<>();
		for (QueuedCandidate candidate : tied) {
			candidates.add(candidate);
			resolved.add(candidate.toMergeCandidate());
		}
		return resolved;
	}

	public void merge(TestCaseCluster first, TestCaseCluster second, TestCaseCluster merged) {
		activeClusters.remove(first);
		activeClusters.remove(second);
		for (TestCaseCluster cluster : activeClusters) {
			addCombinedCandidate(merged, cluster, first, second);
		}
		activeClusters.add(merged);
		dropSimilarities(first);
		dropSimilarities(second);
		compactIfNeeded();
		completedMerges++;
		progress.report(completedMerges, totalWork);
	}

	public void complete() {
		progress.report(totalWork, totalWork);
	}

	private void addInitialCandidates(List<TestCaseCluster> clusters) {
		for (int first = 0; first < clusters.size(); first++) {
			for (int second = first + 1; second < clusters.size(); second++) {
				addEvaluatedCandidate(clusters.get(first), clusters.get(second));
			}
		}
	}

	private void addEvaluatedCandidate(TestCaseCluster left, TestCaseCluster right) {
		TestCaseCluster first = orderedFirst(left, right);
		TestCaseCluster second = first == left ? right : left;
		double similarity = linkage.evaluate(matrix, first, second);
		storeSimilarity(first, second, similarity);
		candidates.add(new QueuedCandidate(first, second, similarity));
	}

	private void addCombinedCandidate(
			TestCaseCluster merged,
			TestCaseCluster other,
			TestCaseCluster firstParent,
			TestCaseCluster secondParent) {
		double similarity = linkage.combinedSimilarity(
				storedSimilarity(firstParent, other),
				storedSimilarity(secondParent, other),
				firstParent.size(),
				secondParent.size());
		TestCaseCluster first = orderedFirst(merged, other);
		TestCaseCluster second = first == merged ? other : merged;
		storeSimilarity(first, second, similarity);
		candidates.add(new QueuedCandidate(first, second, similarity));
	}

	private TestCaseCluster orderedFirst(TestCaseCluster left, TestCaseCluster right) {
		return left.firstTestCaseIndex() <= right.firstTestCaseIndex() ? left : right;
	}

	private void storeSimilarity(TestCaseCluster first, TestCaseCluster second, double similarity) {
		similarities.computeIfAbsent(first, key -> new IdentityHashMap<>()).put(second, similarity);
		similarities.computeIfAbsent(second, key -> new IdentityHashMap<>()).put(first, similarity);
	}

	private double storedSimilarity(TestCaseCluster first, TestCaseCluster second) {
		Map<TestCaseCluster, Double> row = similarities.get(first);
		if (row != null) {
			Double similarity = row.get(second);
			if (similarity != null) {
				return similarity;
			}
		}
		return linkage.evaluate(matrix, orderedFirst(first, second), first == orderedFirst(first, second) ? second : first);
	}

	private void dropSimilarities(TestCaseCluster cluster) {
		Map<TestCaseCluster, Double> row = similarities.remove(cluster);
		if (row == null) {
			return;
		}
		for (TestCaseCluster other : row.keySet()) {
			Map<TestCaseCluster, Double> otherRow = similarities.get(other);
			if (otherRow != null) {
				otherRow.remove(cluster);
			}
		}
	}

	private void compactIfNeeded() {
		int remaining = activeClusters.size();
		long livePairs = remaining <= 1 ? 0 : (long) remaining * (remaining - 1) / 2;
		if (livePairs == 0 || candidates.size() <= STALE_CANDIDATE_RATIO * livePairs) {
			return;
		}
		PriorityQueue<QueuedCandidate> compacted = new PriorityQueue<>();
		for (QueuedCandidate candidate : candidates) {
			if (candidate.isActive(activeClusters)) {
				compacted.add(candidate);
			}
		}
		candidates = compacted;
	}

	private static final class QueuedCandidate implements Comparable<QueuedCandidate> {

		private final TestCaseCluster first;
		private final TestCaseCluster second;
		private final double similarity;

		private QueuedCandidate(TestCaseCluster first, TestCaseCluster second, double similarity) {
			this.first = first;
			this.second = second;
			this.similarity = similarity;
		}

		private boolean isActive(Set<TestCaseCluster> activeClusters) {
			return activeClusters.contains(first) && activeClusters.contains(second);
		}

		private MergeCandidate toMergeCandidate() {
			return new MergeCandidate(new ClusterPair(first, second), similarity);
		}

		@Override
		public int compareTo(QueuedCandidate other) {
			return Double.compare(other.similarity, similarity);
		}
	}
}
