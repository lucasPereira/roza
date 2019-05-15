package br.ufsc.ine.leb.roza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SimilarityReport {

	private List<SimilarityAssessment> assessments;

	public SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = new ArrayList<>(assessments);
	}

	public void sort(Comparator<SimilarityAssessment> comparator) {
		Collections.sort(this.assessments, comparator);
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
