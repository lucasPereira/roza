package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;

public class DendogramTestCaseClusterer implements TestCaseClusterer {

	private Linkage linkage;
	private Referee referee;

	public DendogramTestCaseClusterer(Linkage linkage, Referee referee) {
		this.linkage = linkage;
		this.referee = referee;
	}

	@Override
	public Set<Cluster> cluster(SimilarityReport report) {
		Set<Cluster> clusters = new ClusterFactory().create(report);
		Level level = new Level(linkage, referee, clusters);
		while (level.hasNextLevel()) {
			level = level.generateNextLevel();
		}
		return level.getClusters();
	}

}
