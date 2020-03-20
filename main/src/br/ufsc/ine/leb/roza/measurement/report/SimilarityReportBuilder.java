package br.ufsc.ine.leb.roza.measurement.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;

public class SimilarityReportBuilder {

	private Map<TestCase, Map<TestCase, BigDecimal>> map;

	public SimilarityReportBuilder() {
		map = new HashMap<>();
	}

	public SimilarityReport build() {
		List<SimilarityAssessment> assessments = new ArrayList<SimilarityAssessment>();
		for (Entry<TestCase, Map<TestCase, BigDecimal>> sourceEntry : map.entrySet()) {
			TestCase source = sourceEntry.getKey();
			for (Entry<TestCase, Map<TestCase, BigDecimal>> targetEntry : map.entrySet()) {
				TestCase target = targetEntry.getKey();
				BigDecimal score = map.get(source).containsKey(target) ? map.get(source).get(target) : BigDecimal.ZERO;
				SimilarityAssessment assessment = new SimilarityAssessment(sourceEntry.getKey(), targetEntry.getKey(), score);
				assessments.add(assessment);
			}
		}
		return new SimilarityReport(assessments);
	}

	public SimilarityReportBuilder add(TestCase test) {
		addTest(test);
		return this;
	}

	public SimilarityReportBuilder add(TestCase source, TestCase target) {
		add(source, target, BigDecimal.ZERO);
		return this;
	}

	public SimilarityReportBuilder add(TestCase source, TestCase target, BigDecimal score) {
		add(source, score, target, score);
		return this;
	}

	public SimilarityReportBuilder add(TestCase source, BigDecimal scoreFromSourceToTarget, TestCase target, BigDecimal scoreFromTargetToSource) {
		addTest(target);
		addTest(source);
		if (!source.equals(target)) {
			map.get(source).put(target, scoreFromSourceToTarget);
			map.get(target).put(source, scoreFromTargetToSource);
		}
		return this;
	}

	private void addTest(TestCase test) {
		if (!map.containsKey(test)) {
			map.put(test, new HashMap<>());
		}
		map.get(test).put(test, BigDecimal.ONE);
	}

}
