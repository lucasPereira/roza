package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.exceptions.ClusteringLevelGenerationException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

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
		Set<Cluster> clusters = new ClusterFactory().create(report);
		List<Level> levels = new ArrayList<>();
		Level level = new Level(linkage, referee, clusters);
		levels.add(level);
		while (level.hasNextLevel() && !criteria.shoudlStop(levels)) {
			try {
				level = level.generateNextLevel();
			} catch (TiebreakException exception) {
				throw new ClusteringLevelGenerationException(levels, exception);
			}
			levels.add(level);
		}
		return levels;
	}

}
