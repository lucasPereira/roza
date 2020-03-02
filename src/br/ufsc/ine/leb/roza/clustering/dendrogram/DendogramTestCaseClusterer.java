package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;

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
		Set<Cluster> clusters = new ClusterFactory().create(report);
		List<Level> levels = new ArrayList<>();
		Level level = new Level(linkage, referee, clusters);
		levels.add(level);
		while (level.hasNextLevel() && !criteria.shoudlStop(levels)) {
			level = level.generateNextLevel();
			levels.add(level);
		}
		return level.getClusters();
	}

}
