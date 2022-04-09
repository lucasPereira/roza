package br.ufsc.ine.leb.roza.extraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;

public class JavaTestCaseExtractor implements TestCaseExtractor {

	@Override
	public List<TestCase> extract(List<TestClass> testClasses) {
		List<TestCase> testCases = new LinkedList<>();
		testClasses.forEach((testClass) -> {
			testClass.getTestMethods().forEach(testMethod -> {
				String name = testMethod.getName();
				List<RozaStatement> fixtures = new ArrayList<>();
				List<RozaStatement> assertions = new LinkedList<>();
				testMethod.getStatements().forEach(statement -> {
					fixtures.add(statement);
				});
				TestCase testCase = new TestCase(name, fixtures, assertions);
				testCases.add(testCase);
			});
		});
		return testCases;
	}

}
