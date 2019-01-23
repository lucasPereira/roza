package br.ufsc.ine.leb.roza.extractor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;

public class Junit4TestCaseExtractor implements TestCaseExtractor {

	@Override
	public List<TestCase> extract(List<TestClass> testClasses) {
		List<TestCase> testCases = new LinkedList<>();
		String name = testClasses.get(0).getTestMethods().get(0).getName();
		List<Statement> statements = testClasses.get(0).getTestMethods().get(0).getStatements();
		List<Statement> fixtures = new LinkedList<>();
		List<Statement> assertions = new LinkedList<>();
		statements.forEach((statement) -> {
			List<Statement> bucket = (statementIsAssertion(statement)) ? assertions : fixtures;
			bucket.add(statement);
		});
		TestCase testCase = new TestCase(name, Arrays.asList(), assertions);
		testCases.add(testCase);
		return testCases;
	}

	private Boolean statementIsAssertion(Statement statement) {
		return false;
	}

}
