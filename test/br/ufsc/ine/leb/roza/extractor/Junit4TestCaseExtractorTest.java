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
	void oneTestMethodWithOneFixtureAndZeroAsserts() throws Exception {
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

	@Test
	void oneTestMethodWithTwoFixturesAndTwoAsserts() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(0);");
		Statement fixtureStatement2 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(0, 0);");
		Statement assertStatement2 = new Statement("assertEquals(1, 1);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement1, assertStatement1, fixtureStatement2, assertStatement2));
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(2, testCases.get(0).getFixtures().size());
		assertEquals("sut(0);", testCases.get(0).getFixtures().get(0).getText());
		assertEquals("sut(1);", testCases.get(0).getFixtures().get(1).getText());
		assertEquals(2, testCases.get(0).getAsserts().size());
		assertEquals("assertEquals(0, 0);", testCases.get(0).getAsserts().get(0).getText());
		assertEquals("assertEquals(1, 1);", testCases.get(0).getAsserts().get(1).getText());
	}

	@Test
	void oneTestMethodWithAllAssertsOfJunit() throws Exception {
		List<Statement> statements = Arrays.asList(new Statement("assertArrayEquals(new Object[0], new Object[0]);"), new Statement("assertEquals(0, 0);"), new Statement("assertFalse(false);"), new Statement("assertNotNull(null);"), new Statement("assertNotSame(null, null);"), new Statement("assertNull(null);"), new Statement("assertSame(null, null);"), new Statement("assertThat(null, IsNull.nullValue());"), new Statement("assertTrue(true);"));
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(9, testCases.get(0).getAsserts().size());
		assertEquals("assertArrayEquals(new Object[0], new Object[0]);", testCases.get(0).getAsserts().get(0).getText());
		assertEquals("assertEquals(0, 0);", testCases.get(0).getAsserts().get(1).getText());
		assertEquals("assertFalse(false);", testCases.get(0).getAsserts().get(2).getText());
		assertEquals("assertNotNull(null);", testCases.get(0).getAsserts().get(3).getText());
		assertEquals("assertNotSame(null, null);", testCases.get(0).getAsserts().get(4).getText());
		assertEquals("assertNull(null);", testCases.get(0).getAsserts().get(5).getText());
		assertEquals("assertSame(null, null);", testCases.get(0).getAsserts().get(6).getText());
		assertEquals("assertThat(null, IsNull.nullValue());", testCases.get(0).getAsserts().get(7).getText());
		assertEquals("assertTrue(true);", testCases.get(0).getAsserts().get(8).getText());
	}

}
