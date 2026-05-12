package br.ufsc.ine.leb.roza.core.modern.clustering;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
		Objects.requireNonNull(matrix);
		List<ClusteringLevel> levels = new ArrayList<>();
		List<TestCaseCluster> currentClusters = initialClusters(matrix);
		levels.add(new ClusteringLevel(0, currentClusters));
		while (currentClusters.size() > 1) {
			List<MergeCandidate> bestCandidates = bestCandidates(matrix, currentClusters);
			MergeCandidate candidate = bestCandidates.size() == 1 ? bestCandidates.get(0)
					: tieBreaker.breakTie(bestCandidates).orElseThrow(() -> new IllegalStateException("Could not resolve merge tie."));
			StopCriterionContext context = new StopCriterionContext(currentClusters, candidate, levels.size(), currentClusters.size() - 1);
			if (stopCriterion.shouldStop(context)) {
				break;
			}
			currentClusters = merge(currentClusters, candidate);
			levels.add(new ClusteringLevel(levels.size(), currentClusters, candidate));
		}
		return levels;
	}

	private List<TestCaseCluster> initialClusters(TestCaseSimilarityMatrix matrix) {
		List<TestCaseCluster> clusters = new ArrayList<>();
		for (int index = 0; index < matrix.size(); index++) {
			clusters.add(new TestCaseCluster(index, matrix.testCaseAt(index)));
		}
		return clusters;
	}

	private List<MergeCandidate> bestCandidates(TestCaseSimilarityMatrix matrix, List<TestCaseCluster> clusters) {
		List<MergeCandidate> candidates = new ArrayList<>();
		double best = Double.NEGATIVE_INFINITY;
		for (int first = 0; first < clusters.size(); first++) {
			for (int second = first + 1; second < clusters.size(); second++) {
				TestCaseCluster firstCluster = clusters.get(first);
				TestCaseCluster secondCluster = clusters.get(second);
				double similarity = linkage.evaluate(matrix, firstCluster, secondCluster);
				int comparison = Double.compare(similarity, best);
				if (comparison > 0) {
					candidates.clear();
					best = similarity;
				}
				if (Double.compare(similarity, best) == 0) {
					candidates.add(new MergeCandidate(new ClusterPair(firstCluster, secondCluster), similarity));
				}
			}
		}
		return candidates;
	}

	private List<TestCaseCluster> merge(List<TestCaseCluster> clusters, MergeCandidate candidate) {
		TestCaseCluster first = candidate.pair().first();
		TestCaseCluster second = candidate.pair().second();
		List<TestCaseCluster> merged = clusters.stream()
				.filter(cluster -> cluster != first && cluster != second)
				.collect(Collectors.toCollection(ArrayList::new));
		merged.add(candidate.mergedCluster());
		merged.sort(Comparator.comparingInt(TestCaseCluster::firstTestCaseIndex));
		return merged;
	}
}
