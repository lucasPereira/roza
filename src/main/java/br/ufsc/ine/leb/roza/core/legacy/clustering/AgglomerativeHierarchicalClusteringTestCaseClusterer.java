package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;
import br.ufsc.ine.leb.roza.core.legacy.exceptions.ClusteringLevelGenerationException;
import br.ufsc.ine.leb.roza.core.legacy.exceptions.TiebreakException;

public class AgglomerativeHierarchicalClusteringTestCaseClusterer implements TestCaseClusterer {

	private final Linkage linkage;
	private final Referee referee;
	private final ThresholdCriterion criterion;
	private final ClusteringStatistics statistics;

	public AgglomerativeHierarchicalClusteringTestCaseClusterer(Linkage linkage, Referee referee, ThresholdCriterion criterion) {
		this(linkage, referee, criterion, new ClusteringStatistics());
	}

	public AgglomerativeHierarchicalClusteringTestCaseClusterer(Linkage linkage, Referee referee, ThresholdCriterion criterion, ClusteringStatistics statistics) {
		this.linkage = linkage;
		this.referee = referee;
		this.criterion = criterion;
		this.statistics = statistics;
	}

	@Override
	public Set<Cluster> cluster(SimilarityReport report) {
		List<Level> levels = generateLevels(report);
		return levels.get(levels.size() - 1).getClusters();
	}

	public List<Level> generateLevels(SimilarityReport report) {
		Set<Cluster> currentClusters = new ClusterFactory().create(report);
		MergeCandidateQueue mergeCandidates = new MergeCandidateQueue(currentClusters, linkage, referee, statistics);
		List<Level> levels = new ArrayList<>();
		Level currentLevel = new Level(currentClusters);
		levels.add(currentLevel);
		boolean shouldContinue = currentClusters.size() > 1;
		while (shouldContinue) {
			try {
				WinnerCombination winner = mergeCandidates.next();
				Combination combination = winner.getCombination();
				BigDecimal evaluation = winner.getEvaluation();
				Cluster first = combination.getFirst();
				Cluster second = combination.getSecond();
				Cluster merged = first.merge(second);
				Set<Cluster> nextClusters = currentClusters.stream().filter(cluster -> !cluster.equals(first) && !cluster.equals(second)).collect(Collectors.toSet());
				nextClusters.add(merged);
				Level nextLevel = new Level(currentLevel, nextClusters, evaluation);
				Boolean thresholdReached = criterion.shouldStop(nextLevel.getStep(), combination, evaluation);
				boolean hasClustersToMerge = nextClusters.size() > 1;
				shouldContinue = !thresholdReached && hasClustersToMerge;
				if (!thresholdReached) {
					levels.add(nextLevel);
				}
				if (shouldContinue) {
					mergeCandidates.merge(first, second, merged);
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
