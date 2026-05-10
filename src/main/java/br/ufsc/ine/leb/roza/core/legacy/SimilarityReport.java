package br.ufsc.ine.leb.roza.core.legacy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.core.legacy.exceptions.MissingPairException;

public class SimilarityReport {

	private final List<SimilarityAssessment> assessments;
	private final Map<TestCase, Map<TestCase, SimilarityAssessment>> index;

	SimilarityReport(List<SimilarityAssessment> assessments) {
		this.assessments = new ArrayList<>(assessments);
		this.index = new HashMap<>();
		for (SimilarityAssessment assessment : assessments) {
			index.computeIfAbsent(assessment.getSource(), source -> new HashMap<>()).put(assessment.getTarget(), assessment);
		}
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

	public SimilarityReport removeNonReflexives() {
		List<SimilarityAssessment> filtered = new ArrayList<>();
		for (SimilarityAssessment assessment : assessments) {
			if (assessment != null && assessment.getSource().equals(assessment.getTarget())) {
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

	public SimilarityReport sort(Comparator<SimilarityAssessment> comparator) {
		List<SimilarityAssessment> ordered = new ArrayList<>(assessments);
		ordered.sort(comparator);
		return new SimilarityReport(ordered);
	}

	public SimilarityAssessment getPair(TestCase source, TestCase target) {
		Map<TestCase, SimilarityAssessment> sourceAssessments = index.get(source);
		if (sourceAssessments != null && sourceAssessments.containsKey(target)) {
			return sourceAssessments.get(target);
		}
		throw new MissingPairException(source, target);
	}

	@Override
	public String toString() {
		StringBuilder text = new StringBuilder();
		assessments.forEach(assessment -> {
			text.append(assessment.toString());
			text.append(System.lineSeparator());
		});
		return text.toString();
	}

}
