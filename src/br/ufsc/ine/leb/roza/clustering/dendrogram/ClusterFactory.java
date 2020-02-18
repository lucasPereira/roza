package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

class ClusterFactory {

	public List<Cluster> create(SimilarityReport similarityReport) {
		List<Cluster> clusters = new ArrayList<>();
		for (TestCase test : similarityReport.getUniqueTestCases()) {
			Cluster cluster = new Cluster(test);
			clusters.add(cluster);
		}
		return clusters;
	}

}
