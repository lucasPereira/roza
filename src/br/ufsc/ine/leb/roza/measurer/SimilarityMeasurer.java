package br.ufsc.ine.leb.roza.measurer;

import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public interface SimilarityMeasurer {

	SimilarityReport measure(List<TestCase> testCases);

}
