package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;

public interface TestCaseClusterer {

	Set<Cluster> cluster(SimilarityReport report);

}
