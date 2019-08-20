package br.ufsc.ine.leb.roza.clustering;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class InitialDendogramClusterCreator {

	public List<DendogramCluster> create(SimilarityReport similarityReport) {
		List<DendogramCluster> clusters = new ArrayList<>();
		for (TestCase test : similarityReport.getUniqueTestCases()) {
			DendogramCluster cluster = new DendogramCluster(test);
			clusters.add(cluster);
		}
		return clusters;
	}

}
