package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.utils.ReportUtils;

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
