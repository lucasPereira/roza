package br.ufsc.ine.leb.roza.measurer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class JplagSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public SimilarityReport measure(List<TestCase> testCases) {
		List<SimilarityAssessment> assesssments = new LinkedList<>();
		for (TestCase source : testCases) {
			for (TestCase target : testCases) {
				SimilarityAssessment assessment = new SimilarityAssessment(source, target, BigDecimal.ONE);
				assesssments.add(assessment);
			}
		}
		return new SimilarityReport(assesssments);
	}

}
