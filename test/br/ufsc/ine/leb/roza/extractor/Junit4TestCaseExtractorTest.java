package br.ufsc.ine.leb.roza.extractor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.SetupMethod;
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
	void oneTestMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodOneSetupMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList());
		TestClass testClass = new TestClass("Example", Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneAssert() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneFixture() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneAssert() throws Exception {
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement2));
		TestClass testClass = new TestClass("Example", Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(2, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement1, testCases.get(0).getAsserts().get(0));
		assertEquals(assertStatement2, testCases.get(0).getAsserts().get(1));
	}

	@Test
	void oneTestMethodWithOneFixture() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneFixture() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement fixtureStatement2 = new Statement("sut(2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement2));
		TestClass testClass = new TestClass("Example", Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(2, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement1, testCases.get(0).getFixtures().get(0));
		assertEquals(fixtureStatement2, testCases.get(0).getFixtures().get(1));
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneAssert() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		Statement fixtureStatement = new Statement("sut(0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("Example", Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void complex() throws Exception {
		fail();
	}

	@Test
	void oneTestMethodWithAllAssertsOfJunit() throws Exception {
		Statement arrayEqualsAssertion = new Statement("assertArrayEquals(new Object[0], new Object[0]);");
		Statement equalsAssertion = new Statement("assertEquals(0, 0);");
		Statement falseAssertion = new Statement("assertFalse(false);");
		Statement notNullAssertion = new Statement("assertNotNull(null);");
		Statement notSameAssertion = new Statement("assertNotSame(null, null);");
		Statement nullAssertion = new Statement("assertNull(null);");
		Statement sameAssertion = new Statement("assertSame(null, null);");
		Statement thatAssertion = new Statement("assertThat(null, IsNull.nullValue());");
		Statement trueAssertion = new Statement("assertTrue(true);");
		List<Statement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(9, testCases.get(0).getAsserts().size());
		assertEquals(arrayEqualsAssertion, testCases.get(0).getAsserts().get(0));
		assertEquals(equalsAssertion, testCases.get(0).getAsserts().get(1));
		assertEquals(falseAssertion, testCases.get(0).getAsserts().get(2));
		assertEquals(notNullAssertion, testCases.get(0).getAsserts().get(3));
		assertEquals(notSameAssertion, testCases.get(0).getAsserts().get(4));
		assertEquals(nullAssertion, testCases.get(0).getAsserts().get(5));
		assertEquals(sameAssertion, testCases.get(0).getAsserts().get(6));
		assertEquals(thatAssertion, testCases.get(0).getAsserts().get(7));
		assertEquals(trueAssertion, testCases.get(0).getAsserts().get(8));
	}

	@Test
	void oneTestMethodWithAllNonStaticAssertsOfJunit() throws Exception {
		Statement arrayEqualsAssertion = new Statement("Assert.assertArrayEquals(new Object[0], new Object[0]);");
		Statement equalsAssertion = new Statement("Assert.assertEquals(0, 0);");
		Statement falseAssertion = new Statement("Assert.assertFalse(false);");
		Statement notNullAssertion = new Statement("Assert.assertNotNull(null);");
		Statement notSameAssertion = new Statement("Assert.assertNotSame(null, null);");
		Statement nullAssertion = new Statement("Assert.assertNull(null);");
		Statement sameAssertion = new Statement("Assert.assertSame(null, null);");
		Statement thatAssertion = new Statement("Assert.assertThat(null, IsNull.nullValue());");
		Statement trueAssertion = new Statement("Assert.assertTrue(true);");
		List<Statement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("Example", Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(9, testCases.get(0).getAsserts().size());
		assertEquals(arrayEqualsAssertion, testCases.get(0).getAsserts().get(0));
		assertEquals(equalsAssertion, testCases.get(0).getAsserts().get(1));
		assertEquals(falseAssertion, testCases.get(0).getAsserts().get(2));
		assertEquals(notNullAssertion, testCases.get(0).getAsserts().get(3));
		assertEquals(notSameAssertion, testCases.get(0).getAsserts().get(4));
		assertEquals(nullAssertion, testCases.get(0).getAsserts().get(5));
		assertEquals(sameAssertion, testCases.get(0).getAsserts().get(6));
		assertEquals(thatAssertion, testCases.get(0).getAsserts().get(7));
		assertEquals(trueAssertion, testCases.get(0).getAsserts().get(8));
	}

}
