package br.ufsc.ine.leb.roza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimilarityReport {

	private List<SimilarityAssessment> assessments;

	public SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = new ArrayList<>(assessments);
		order();
	}

	private void order() {
		Collections.sort(this.assessments, new Comparator<SimilarityAssessment>() {

			@Override
			public int compare(SimilarityAssessment assessment1, SimilarityAssessment assessment2) {
				Integer scoreComparison = -assessment1.getScore().compareTo(assessment2.getScore());
				Integer sourceComparison = assessment1.getSource().getId().compareTo(assessment2.getSource().getId());
				Integer targetComparison = assessment1.getTarget().getId().compareTo(assessment2.getTarget().getId());
				return scoreComparison != 0 ? scoreComparison : (sourceComparison != 0 ? sourceComparison : targetComparison);
			}

		});
	}

	public List<SimilarityAssessment> getAssessments() {
		return Collections.unmodifiableList(assessments);
	}

	public void removeReflexiveAssessments() {
		assessments.removeIf((assessment) -> {
			return assessment == null || assessment.getSource().equals(assessment.getTarget());
		});
	}

}
