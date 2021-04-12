package br.ufsc.ine.leb.roza.refactoring;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.utils.comparator.ClusterComparatorBySizeAndTestName;
import br.ufsc.ine.leb.roza.utils.comparator.TestCaseComparatorByName;

public class SimpleClusterRefactor {

	public List<TestClass> refactor(Set<Cluster> clusters) {
		Integer contador = 0;
		List<TestClass> classes = new ArrayList<TestClass>(clusters.size());
		List<Cluster> clustersOrderedBySizeAndTestName = new ArrayList<>(clusters);
		Collections.sort(clustersOrderedBySizeAndTestName, new ClusterComparatorBySizeAndTestName());
		for (Cluster cluster : clustersOrderedBySizeAndTestName) {
			List<TestMethod> testMethods = new ArrayList<>(cluster.getTestCases().size());
			List<TestCase> testCases = new ArrayList<>(cluster.getTestCases());
			Collections.sort(testCases, new TestCaseComparatorByName());
			for (TestCase testCase : testCases) {
				TestMethod testMethod = new TestMethod(testCase.getName(), Arrays.asList());
				testMethods.add(testMethod);
			}
			TestClass testClass = new TestClass("RefactoredTestClass" + ++contador, Arrays.asList(), Arrays.asList(), testMethods);
			classes.add(testClass);
		}
		return classes;
	}

}
