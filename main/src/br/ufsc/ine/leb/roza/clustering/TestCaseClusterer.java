package br.ufsc.ine.leb.roza.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.measurement.SimilarityReport;

public interface TestCaseClusterer {

	Set<Cluster> cluster(SimilarityReport report);

}
