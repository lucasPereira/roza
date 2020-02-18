package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

class ClusterFactory {

	public List<Cluster> create(SimilarityReport similarityReport) {
		ReportUtils reportUtils = new ReportUtils();
		List<Cluster> clusters = new ArrayList<>();
		for (TestCase test : reportUtils.getUniqueTestCases(similarityReport)) {
			Cluster cluster = new Cluster(test);
			clusters.add(cluster);
		}
		return clusters;
	}

}
