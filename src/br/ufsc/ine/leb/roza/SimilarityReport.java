package br.ufsc.ine.leb.roza;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimilarityReport<T> {

	private List<SimilarityAssessment<T>> assessments;

	public SimilarityReport(List<SimilarityAssessment<T>> assessments) {
		this.assessments = new ArrayList<>(assessments);
	}

	public List<SimilarityAssessment<T>> getAssessments() {
		return Collections.unmodifiableList(assessments);
	}

	public void removeReflexiveAssessments() {
		assessments.removeIf((assessment) -> {
			return assessment.getSource().equals(assessment.getTarget());
		});
	}

	public BigDecimal getScore() {
		removeReflexiveAssessments();
		BigDecimal total = new BigDecimal(0);
		for (SimilarityAssessment<T> similarityAssessment : assessments) {
			total = total.add(similarityAssessment.getScore());
		}
		if (assessments.size() < 1)
			return BigDecimal.ONE;
		return total.divide(new BigDecimal(assessments.size()));
	}

}
