package br.ufsc.ine.leb.roza.extractor;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;

public class Junit4TestCaseExtractor implements TestCaseExtractor {

	@Override
	public List<TestCase> extract(List<TestClass> testClasses) {
		List<TestCase> testCases = new LinkedList<>();
		TestCase testCase = new TestCase(testClasses.get(0).getTestMethods().get(0).getName(), Arrays.asList(), Arrays.asList());
		testCases.add(testCase);
		return testCases;
	}

}
