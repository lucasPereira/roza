package br.ufsc.ine.leb.roza.measurer.report;

import java.util.Comparator;

import br.ufsc.ine.leb.roza.SimilarityAssessment;

public class ScoreAndTestCaseNameAssessmentComparator implements Comparator<SimilarityAssessment>{

	@Override
	public int compare(SimilarityAssessment assessment1, SimilarityAssessment assessment2) {
		Integer scoreComparison = -assessment1.getScore().compareTo(assessment2.getScore());
		Integer sourceComparison = assessment1.getSource().getId().compareTo(assessment2.getSource().getId());
		Integer targetComparison = assessment1.getTarget().getId().compareTo(assessment2.getTarget().getId());
		return scoreComparison != 0 ? scoreComparison : (sourceComparison != 0 ? sourceComparison : targetComparison);
	}

}
