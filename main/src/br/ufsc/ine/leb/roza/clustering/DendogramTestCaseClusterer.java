package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.exceptions.ClusteringLevelGenerationException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;
import br.ufsc.ine.leb.roza.measurement.SimilarityReport;

public class DendogramTestCaseClusterer implements TestCaseClusterer {

	private Linkage linkage;
	private Referee referee;
	private ThresholdCriteria criteria;

	public DendogramTestCaseClusterer(Linkage linkage, Referee referee, ThresholdCriteria criteria) {
		this.linkage = linkage;
		this.referee = referee;
		this.criteria = criteria;
	}

	@Override
	public Set<Cluster> cluster(SimilarityReport report) {
		List<Level> levels = generateLevels(report);
		return levels.get(levels.size() - 1).getClusters();
	}

	public List<Level> generateLevels(SimilarityReport report) {
		ClusterJoiner joiner = new ClusterJoiner(linkage, referee);
		Set<Cluster> currentClusters = new ClusterFactory().create(report);
		List<Level> levels = new ArrayList<>();
		Level currentLevel = new Level(currentClusters);
		levels.add(currentLevel);
		Boolean shouldContinue = currentClusters.size() > 1;
		while (shouldContinue) {
			ClustersToMerge clustersToMerge = new ClustersToMerge(currentClusters);
			try {
				WinnerCombination winner = joiner.join(clustersToMerge);
				Combination combination = winner.getCombination();
				BigDecimal evaluation = winner.getEvaluation();
				Cluster first = combination.getFirst();
				Cluster second = combination.getSecond();
				Cluster merged = first.merge(second);
				Set<Cluster> nextClusters = currentClusters.stream().filter(cluster -> !cluster.equals(first) && !cluster.equals(second)).collect(Collectors.toSet());
				nextClusters.add(merged);
				Level nextLevel = new Level(currentLevel, nextClusters, evaluation);
				Boolean thresholdReached = criteria.shoudlStop(nextLevel.getStep(), combination, evaluation);
				Boolean hasClustersToMerge = nextClusters.size() > 1;
				shouldContinue = !thresholdReached && hasClustersToMerge;
				if (!thresholdReached) {
					levels.add(nextLevel);
				}
				if (shouldContinue) {
					currentLevel = nextLevel;
					currentClusters = nextClusters;
				}
			} catch (TiebreakException exception) {
				throw new ClusteringLevelGenerationException(levels, exception);
			}
		}
		return levels;
	}

}
