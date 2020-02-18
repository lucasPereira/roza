package br.ufsc.ine.leb.roza.utils;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.measurement.report.TestCaseNameComparator;

public class ReportUtils {

	public List<TestCase> getTargets(SimilarityReport similarityReport) {
		List<TestCase> targets = new ArrayList<>();
		for (SimilarityAssessment assessment : similarityReport.getAssessments()) {
			targets.add(assessment.getTarget());
		}
		return targets;
	}

	public List<TestCase> getUniqueTestCases(SimilarityReport similarityReport) {
		List<TestCase> testCases = new ArrayList<>();
		for (SimilarityAssessment assessment : similarityReport.getAssessments()) {
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
