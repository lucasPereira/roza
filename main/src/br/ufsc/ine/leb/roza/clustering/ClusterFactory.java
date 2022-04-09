package br.ufsc.ine.leb.roza.clustering;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.measurement.SimilarityReport;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

class ClusterFactory {

	public Set<Cluster> create(SimilarityReport report) {
		ReportUtils reportUtils = new ReportUtils();
		Set<Cluster> clusters = new HashSet<>();
		for (TestCase test : reportUtils.getUniqueTestCases(report)) {
			Cluster cluster = new Cluster(test);
			clusters.add(cluster);
		}
		return clusters;
	}

}
