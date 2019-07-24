package br.ufsc.ine.leb.roza.clustering;

import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public interface TestCaseClusterer {

	List<List<TestCase>> cluster(SimilarityReport similarityReport);

}
