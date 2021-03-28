package br.ufsc.ine.leb.roza.utils;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.utils.comparator.TestCaseComparatorByName;

public class ReportUtils {

	public List<TestCase> getTargets(SimilarityReport report) {
		List<TestCase> targets = new ArrayList<>();
		for (SimilarityAssessment assessment : report.getAssessments()) {
			targets.add(assessment.getTarget());
		}
		return targets;
	}

	public List<TestCase> getUniqueTestCases(SimilarityReport report) {
		List<TestCase> testCases = new ArrayList<>();
		for (SimilarityAssessment assessment : report.getAssessments()) {
			TestCase source = assessment.getSource();
			if (!testCases.contains(source)) {
				testCases.add(source);
			}
			TestCase target = assessment.getTarget();
			if (!testCases.contains(target)) {
				testCases.add(target);
			}
		}
		testCases.sort(new TestCaseComparatorByName());
		return testCases;
	}

}
