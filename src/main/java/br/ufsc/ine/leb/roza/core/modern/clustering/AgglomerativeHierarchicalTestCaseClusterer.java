package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;
import br.ufsc.ine.leb.roza.core.modern.measurement.TestCaseSimilarityMatrix;

public final class AgglomerativeHierarchicalTestCaseClusterer implements TestCaseClusterer {

	private final ClusterLinkage linkage;
	private final StopCriterion stopCriterion;
	private final MergeTieBreaker tieBreaker;

	public AgglomerativeHierarchicalTestCaseClusterer(ClusterLinkage linkage, StopCriterion stopCriterion, MergeTieBreaker tieBreaker) {
		this.linkage = Objects.requireNonNull(linkage);
		this.stopCriterion = Objects.requireNonNull(stopCriterion);
		this.tieBreaker = Objects.requireNonNull(tieBreaker);
	}

	@Override
	public TestCaseClusters cluster(TestCaseSimilarityMatrix matrix) {
		List<ClusteringLevel> levels = generateLevels(matrix);
		return new TestCaseClusters(levels.get(levels.size() - 1).clusters());
	}

	public List<ClusteringLevel> generateLevels(TestCaseSimilarityMatrix matrix) {
		return generateLevels(matrix, StageProgress.ignore());
	}

	public List<ClusteringLevel> generateLevels(TestCaseSimilarityMatrix matrix, StageProgress progress) {
		Objects.requireNonNull(matrix);
		StageProgress reporter = progress == null ? StageProgress.ignore() : progress;
		List<ClusteringLevel> levels = new ArrayList<>();
		List<TestCaseCluster> currentClusters = initialClusters(matrix);
		levels.add(new ClusteringLevel(0, currentClusters));
		MergeCandidateQueue queue = new MergeCandidateQueue(currentClusters, linkage, matrix, reporter);
		while (currentClusters.size() > 1) {
			List<MergeCandidate> bestCandidates = queue.bestCandidates();
			MergeCandidate candidate = bestCandidates.size() == 1 ? bestCandidates.get(0)
					: tieBreaker.breakTie(bestCandidates).orElseThrow(() -> new IllegalStateException("Could not resolve merge tie."));
			StopCriterionContext context = new StopCriterionContext(currentClusters, candidate, levels.size(), currentClusters.size() - 1);
			if (stopCriterion.shouldStop(context)) {
				break;
			}
			TestCaseCluster first = candidate.pair().first();
			TestCaseCluster second = candidate.pair().second();
			TestCaseCluster merged = candidate.mergedCluster();
			currentClusters = merge(currentClusters, first, second, merged);
			queue.merge(first, second, merged);
			levels.add(new ClusteringLevel(levels.size(), currentClusters, candidate));
		}
		queue.complete();
		return levels;
	}

	private List<TestCaseCluster> initialClusters(TestCaseSimilarityMatrix matrix) {
		List<TestCaseCluster> clusters = new ArrayList<>();
		for (int index = 0; index < matrix.size(); index++) {
			clusters.add(new TestCaseCluster(index, matrix.testCaseAt(index)));
		}
		return clusters;
	}

	private List<TestCaseCluster> merge(
			List<TestCaseCluster> clusters,
			TestCaseCluster first,
			TestCaseCluster second,
			TestCaseCluster merged) {
		List<TestCaseCluster> next = clusters.stream()
				.filter(cluster -> cluster != first && cluster != second)
				.collect(Collectors.toCollection(ArrayList::new));
		next.add(merged);
		next.sort(Comparator.comparingInt(TestCaseCluster::firstTestCaseIndex));
		return next;
	}
}
