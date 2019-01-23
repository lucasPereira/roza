package br.ufsc.ine.leb.roza.extractor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

public class Junit4TestCaseExtractorTest {

	private TestCaseExtractor extractor;

	@BeforeEach
	void setup() {
		extractor = new Junit4TestCaseExtractor();
	}

	@Test
	void oneTestMethodWithZeroFixturesAndZeroAsserts() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithZeroFixturesAndOneAssert() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals("assertEquals(0, 0);", testCases.get(0).getAsserts().get(0).getText());
	}

	@Test
	void oneTestMethodWithOneFixtureAndZeroAssert() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals("sut(0);", testCases.get(0).getFixtures().get(0).getText());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}
}
