package br.ufsc.ine.leb.roza;

import java.util.List;

public class SimilarityReport {

	private List<SimilarityAssessment> assessments;

	public SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = assessments;
	}

	public List<SimilarityAssessment> getAssessments() {
		return assessments;
	}

}
