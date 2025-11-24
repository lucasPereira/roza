package br.ufsc.ine.leb.roza.utils;

import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicatedStatementCounterTest {

	private DuplicatedStatementCounter counter;
	private Statement statementX1;
	private Statement statementX2;
	private Statement statementX3;
	private Statement statementY1;
	private Statement statementY2;
	private Statement statementY3;
	private Statement statementZ1;

	@BeforeEach
	void setUp() {
		counter = new DuplicatedStatementCounter();
		statementX1 = new Statement("int x = 1;");
		statementX2 = new Statement("int x = 1;");
		statementX3 = new Statement("int x = 1;");
		statementY1 = new Statement("int y = 2;");
		statementY2 = new Statement("int y = 2;");
		statementY3 = new Statement("int y = 2;");
		statementZ1 = new Statement("int z = 3;");
	}

	@Test
	void emptyTestClasses() {
		var count = counter.count(Collections.emptyList());

		assertEquals(0, count.getUniqueDuplicateCount());
		assertEquals(0, count.getTotalDuplicateCount());
	}

	@Test
	void noDuplicates() {
		TestMethod testMethod = new TestMethod("test", List.of(statementX1, statementY1));
		SetupMethod setupMethod = new SetupMethod("setup", Collections.singletonList(statementZ1));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), List.of(setupMethod), List.of(testMethod));

		var count = counter.count(List.of(testClass));

		assertEquals(0, count.getUniqueDuplicateCount());
		assertEquals(0, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesInSameClass() {
		TestMethod testMethod1 = new TestMethod("test1", List.of(statementX1, statementY1));
		TestMethod testMethod2 = new TestMethod("test2", List.of(statementX2));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), Collections.emptyList(), List.of(testMethod1, testMethod2));

		var count = counter.count(List.of(testClass));

		assertEquals(1, count.getUniqueDuplicateCount());
		assertEquals(1, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesAcrossClasses() {
		TestMethod testMethod1 = new TestMethod("test1", List.of(statementX1, statementY1));
		TestClass testClass1 = new TestClass("TestClass1", Collections.emptyList(), Collections.emptyList(), List.of(testMethod1));

		TestMethod testMethod2 = new TestMethod("test2", List.of(statementX2, statementY2));
		TestClass testClass2 = new TestClass("TestClass2", Collections.emptyList(), Collections.emptyList(), List.of(testMethod2));

		var count = counter.count(List.of(testClass1, testClass2));

		assertEquals(2, count.getUniqueDuplicateCount());
		assertEquals(2, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesWithMultipleOccurrences() {
		TestMethod testMethod1 = new TestMethod("test1", Collections.singletonList(statementX1));
		TestMethod testMethod2 = new TestMethod("test2", List.of(statementX2));
		TestMethod testMethod3 = new TestMethod("test3", List.of(statementX3));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), Collections.emptyList(), List.of(testMethod1, testMethod2, testMethod3));

		var count = counter.count(List.of(testClass));

		assertEquals(1, count.getUniqueDuplicateCount());
		assertEquals(2, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesWithMultipleStatements() {
		TestMethod testMethod1 = new TestMethod("test1", List.of(statementX1, statementY1));
		TestMethod testMethod2 = new TestMethod("test2", List.of(statementX2, statementY2));
		TestMethod testMethod3 = new TestMethod("test3", List.of(statementX3));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), Collections.emptyList(), List.of(testMethod1, testMethod2, testMethod3));

		var count = counter.count(List.of(testClass));

		assertEquals(2, count.getUniqueDuplicateCount());
		assertEquals(3, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesWithSetupAndTestMethods() {
		SetupMethod setupMethod = new SetupMethod("setup", List.of(statementX1, statementY1));
		TestMethod testMethod = new TestMethod("test", List.of(statementX2));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), List.of(setupMethod), List.of(testMethod));

		var count = counter.count(List.of(testClass));

		assertEquals(1, count.getUniqueDuplicateCount());
		assertEquals(1, count.getTotalDuplicateCount());
	}

	@Test
	void duplicatesAcrossSetupMethodsOnly() {
		SetupMethod setupMethod1 = new SetupMethod("setup1", List.of(statementX1));
		SetupMethod setupMethod2 = new SetupMethod("setup2", List.of(statementX2));
		TestMethod testMethod = new TestMethod("test", List.of(statementY1));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), List.of(setupMethod1, setupMethod2), List.of(testMethod));

		var count = counter.count(List.of(testClass));

		assertEquals(1, count.getUniqueDuplicateCount());
		assertEquals(1, count.getTotalDuplicateCount());
	}

	@Test
	void getDuplicatedStatementsWithCounts() {
		TestMethod testMethod1 = new TestMethod("test", List.of(statementX1, statementY1));
		TestMethod testMethod2 = new TestMethod("test", List.of(statementX2, statementY2, statementY3));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), Collections.emptyList(), List.of(testMethod1, testMethod2));

		Map<String, Integer> duplicates = counter.count(List.of(testClass)).getCountByDuplicatedStatement();

		assertEquals(2, duplicates.size());
		assertEquals(2, duplicates.get("int x = 1;"));
		assertEquals(3, duplicates.get("int y = 2;"));
	}

	@Test
	void getDuplicatedStatementsWithCountsReturnsEmptyForNoDuplicates() {
		TestMethod testMethod = new TestMethod("test", List.of(statementX1, statementY1));
		TestClass testClass = new TestClass("TestClass", Collections.emptyList(), Collections.emptyList(), List.of(testMethod));

		Map<String, Integer> duplicates = counter.count(List.of(testClass)).getCountByDuplicatedStatement();

		assertTrue(duplicates.isEmpty());
	}
}
