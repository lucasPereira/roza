package br.ufsc.ine.leb.roza.core.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.SimilarityReport;

public interface TestCaseClusterer {

	Set<Cluster> cluster(SimilarityReport report);

}
