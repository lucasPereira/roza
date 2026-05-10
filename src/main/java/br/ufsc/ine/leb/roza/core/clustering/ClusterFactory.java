package br.ufsc.ine.leb.roza.core.clustering;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.utils.ReportUtils;

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
