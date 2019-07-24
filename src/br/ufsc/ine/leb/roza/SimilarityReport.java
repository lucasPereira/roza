package br.ufsc.ine.leb.roza;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.ufsc.ine.leb.roza.measurement.report.TestCaseNameComparator;

public class SimilarityReport {

	private List<SimilarityAssessment> assessments;

	public SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = new ArrayList<>(assessments);
	}

	public SimilarityReport sort(Comparator<SimilarityAssessment> comparator) {
		Collections.sort(this.assessments, comparator);
		return this;
	}

	public List<SimilarityAssessment> getAssessments() {
		return Collections.unmodifiableList(assessments);
	}

	public List<TestCase> getTargets() {
		List<TestCase> targets = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			targets.add(assessment.getTarget());
		}
		return targets;
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

	public List<TestCase> getUniqueTestCases() {
		List<TestCase> testCases = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			TestCase source = assessment.getSource();
			if (!testCases.contains(source)) {
				testCases.add(source);
			}
			TestCase target = assessment.getTarget();
			if (!testCases.contains(target)) {
				testCases.add(target);
			}
		}
		testCases.sort(new TestCaseNameComparator());
		return testCases;
	}

}
