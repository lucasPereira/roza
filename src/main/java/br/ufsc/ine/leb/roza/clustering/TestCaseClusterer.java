package br.ufsc.ine.leb.roza.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.SimilarityReport;

public interface TestCaseClusterer {

	Set<Cluster> cluster(SimilarityReport report);

}
