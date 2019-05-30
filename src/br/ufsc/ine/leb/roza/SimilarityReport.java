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

	public SimilarityReport removeReflexives() {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && !assessment.getSource().equals(assessment.getTarget())) {
				filtered.add(assessment);
			}
		}
		return new SimilarityReport(filtered);
	}

	public SimilarityReport selectSource(TestCase source) {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && assessment.getSource().equals(source)) {
				filtered.add(assessment);
			}
		}
		return new SimilarityReport(filtered);
	}

}
