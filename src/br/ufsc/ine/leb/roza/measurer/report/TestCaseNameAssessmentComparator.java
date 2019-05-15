package br.ufsc.ine.leb.roza.measurer.report;

import java.util.Comparator;

import br.ufsc.ine.leb.roza.SimilarityAssessment;

public class TestCaseNameAssessmentComparator implements Comparator<SimilarityAssessment>{

	@Override
	public int compare(SimilarityAssessment assessment1, SimilarityAssessment assessment2) {
		Integer sourceComparison = assessment1.getSource().getName().compareTo(assessment2.getSource().getName());
		Integer targetComparison = assessment1.getTarget().getName().compareTo(assessment2.getTarget().getName());
		return sourceComparison == 0 ? targetComparison : sourceComparison;
	}

}
