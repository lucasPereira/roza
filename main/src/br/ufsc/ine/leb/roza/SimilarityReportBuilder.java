package br.ufsc.ine.leb.roza;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.ufsc.ine.leb.roza.exceptions.MissingAssessmentException;
import br.ufsc.ine.leb.roza.exceptions.PotentialErrorProneOperationException;

public class SimilarityReportBuilder {

	private Set<TestCase> tests;
	private Map<TestCase, Map<TestCase, BigDecimal>> map;
	private Boolean symmetric;

	public SimilarityReportBuilder(Boolean symmetric) {
		this.symmetric = symmetric;
		map = new HashMap<>();
		tests = new HashSet<>();
	}

	public SimilarityReportBuilder add(TestCase test) {
		if (tests.contains(test) || map.containsKey(test)) {
			throw new PotentialErrorProneOperationException();
		}
		addInternal(test);
		tests.add(test);
		return this;
	}

	public SimilarityReportBuilder add(TestCase source, TestCase target, BigDecimal score) {
		if (source.equals(target) || tests.contains(source) || tests.contains(target)) {
			throw new PotentialErrorProneOperationException();
		}
		addInternal(source);
		addInternal(target);
		if (map.get(source).containsKey(target)) {
			throw new PotentialErrorProneOperationException();
		}
		map.get(source).put(target, score);
		if (symmetric) {
			map.get(target).put(source, score);
		}
		return this;
	}

	private void addInternal(TestCase test) {
		if (!map.containsKey(test)) {
			map.put(test, new HashMap<>());
			map.get(test).put(test, BigDecimal.ONE);
		}
	}

	public SimilarityReportBuilder complete() {
		for (TestCase source : map.keySet()) {
			for (TestCase target : map.keySet()) {
				if (!map.get(source).containsKey(target)) {
					map.get(source).put(target, BigDecimal.ZERO);
				}
			}
		}
		return this;
	}

	public SimilarityReport build() {
		Set<TestCase> all = map.keySet();
		List<SimilarityAssessment> assessments = new ArrayList<>();
		for (TestCase source : all) {
			for (TestCase target : all) {
				if (map.get(source).containsKey(target)) {
					BigDecimal score = map.get(source).get(target);
					SimilarityAssessment assessment = new SimilarityAssessment(source, target, score);
					assessments.add(assessment);
				}
			}
		}
		Integer size = all.size() * all.size();
		if (size != assessments.size()) {
			throw new MissingAssessmentException();
		}
		return new SimilarityReport(assessments);
	}

}
