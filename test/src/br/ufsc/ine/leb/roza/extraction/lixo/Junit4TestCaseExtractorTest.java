package br.ufsc.ine.leb.roza.extraction.lixo;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.TestClass;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

class Junit4TestCaseExtractorTest {

	private TestCaseExtractor extractor;

	@BeforeEach
	void setup() {
		extractor = new Junit4TestCaseExtractor();
	}

	@Test
	void oneTestMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
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
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneAssert() throws Exception {
		RozaStatement assertStatement = new RozaStatement("assertEquals(0, 0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneFixture() throws Exception {
		RozaStatement fixtureStatement = new RozaStatement("sut(0);");
		RozaStatement assertStatement = new RozaStatement("assertEquals(0, 0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));
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
		RozaStatement assertStatement1 = new RozaStatement("assertEquals(1, 1);");
		RozaStatement assertStatement2 = new RozaStatement("assertEquals(2, 2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement2));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));
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
		RozaStatement fixtureStatement = new RozaStatement("sut(0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneFixture() throws Exception {
		RozaStatement fixtureStatement1 = new RozaStatement("sut(1);");
		RozaStatement fixtureStatement2 = new RozaStatement("sut(2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement2));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));
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
		RozaStatement assertStatement = new RozaStatement("assertEquals(0, 0);");
		RozaStatement fixtureStatement = new RozaStatement("sut(0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void setupMethodWithFieldInicialization() throws Exception {
		RozaStatement sutDeclaretionAndInicialization = new RozaStatement("Sut sut = new Sut();");
		RozaStatement sutInicialization = new RozaStatement("sut = new Sut();");
		RozaStatement saveStatement = new RozaStatement("sut.save(0);");
		RozaStatement assertStatement = new RozaStatement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(sutInicialization));
		Field field = new Field("Sut", "sut");
		TestMethod testMethod = new TestMethod("test", Arrays.asList(saveStatement, assertStatement));
		TestClass testClass = new TestClass("", Arrays.asList(field), Arrays.asList(setupMethod), Arrays.asList(testMethod));
		List<TestCase> testCases = extractor.extract(Arrays.asList(testClass));

		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(2, testCases.get(0).getFixtures().size());
		assertEquals(sutDeclaretionAndInicialization, testCases.get(0).getFixtures().get(0));
		assertEquals(saveStatement, testCases.get(0).getFixtures().get(1));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void complex() throws Exception {
		RozaStatement classASetup1Fixture1 = new RozaStatement("sut(1);");
		RozaStatement classASetup1Fixture2 = new RozaStatement("sut(2);");
		RozaStatement classATest1Fixture3 = new RozaStatement("sut(3);");
		RozaStatement classATest1Fixture4 = new RozaStatement("sut(4);");
		RozaStatement classASetup2Fixture5 = new RozaStatement("sut(5);");
		RozaStatement classBSetup3Fixture6 = new RozaStatement("sut(6);");
		RozaStatement classASetup1Assert1 = new RozaStatement("assertEquals(1, 1);");
		RozaStatement classASetup1Assert2 = new RozaStatement("assertEquals(2, 2);");
		RozaStatement classATest1Assert3 = new RozaStatement("assertEquals(3, 3);");
		RozaStatement classATest1Assert4 = new RozaStatement("assertEquals(4, 4);");
		RozaStatement classATest2Assert5 = new RozaStatement("assertEquals(5, 5);");
		RozaStatement classBTest3Assert6 = new RozaStatement("assertEquals(6, 6);");
		SetupMethod classASetup1 = new SetupMethod("setup1", Arrays.asList(classASetup1Fixture1, classASetup1Fixture2, classASetup1Assert1, classASetup1Assert2));
		SetupMethod classASetup2 = new SetupMethod("setup2", Arrays.asList(classASetup2Fixture5));
		SetupMethod classBSetup3 = new SetupMethod("setup3", Arrays.asList(classBSetup3Fixture6));
		TestMethod classATest1 = new TestMethod("test1", Arrays.asList(classATest1Fixture3, classATest1Fixture4, classATest1Assert3, classATest1Assert4));
		TestMethod classATest2 = new TestMethod("test2", Arrays.asList(classATest2Assert5));
		TestMethod classBTest3 = new TestMethod("test3", Arrays.asList(classBTest3Assert6));
		TestClass classA = new TestClass("A", Arrays.asList(), Arrays.asList(classASetup1, classASetup2), Arrays.asList(classATest1, classATest2));
		TestClass classB = new TestClass("B", Arrays.asList(), Arrays.asList(classBSetup3), Arrays.asList(classBTest3));

		List<TestCase> testCases = extractor.extract(Arrays.asList(classA, classB));
		assertEquals(3, testCases.size());

		TestCase firstTestCase = testCases.get(0);
		assertEquals("test1", firstTestCase.getName());
		assertEquals(5, firstTestCase.getFixtures().size());
		assertEquals(classASetup1Fixture1, firstTestCase.getFixtures().get(0));
		assertEquals(classASetup1Fixture2, firstTestCase.getFixtures().get(1));
		assertEquals(classASetup2Fixture5, firstTestCase.getFixtures().get(2));
		assertEquals(classATest1Fixture3, firstTestCase.getFixtures().get(3));
		assertEquals(classATest1Fixture4, firstTestCase.getFixtures().get(4));
		assertEquals(4, firstTestCase.getAsserts().size());
		assertEquals(classASetup1Assert1, firstTestCase.getAsserts().get(0));
		assertEquals(classASetup1Assert2, firstTestCase.getAsserts().get(1));
		assertEquals(classATest1Assert3, firstTestCase.getAsserts().get(2));
		assertEquals(classATest1Assert4, firstTestCase.getAsserts().get(3));

		TestCase secondTestCase = testCases.get(1);
		assertEquals("test2", secondTestCase.getName());
		assertEquals(3, secondTestCase.getFixtures().size());
		assertEquals(classASetup1Fixture1, secondTestCase.getFixtures().get(0));
		assertEquals(classASetup1Fixture2, secondTestCase.getFixtures().get(1));
		assertEquals(classASetup2Fixture5, secondTestCase.getFixtures().get(2));
		assertEquals(3, secondTestCase.getAsserts().size());
		assertEquals(classASetup1Assert1, secondTestCase.getAsserts().get(0));
		assertEquals(classASetup1Assert2, secondTestCase.getAsserts().get(1));
		assertEquals(classATest2Assert5, secondTestCase.getAsserts().get(2));

		TestCase thirdTestCase = testCases.get(2);
		assertEquals("test3", thirdTestCase.getName());
		assertEquals(1, thirdTestCase.getFixtures().size());
		assertEquals(classBSetup3Fixture6, thirdTestCase.getFixtures().get(0));
		assertEquals(1, thirdTestCase.getAsserts().size());
		assertEquals(classBTest3Assert6, thirdTestCase.getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithAllAssertsOfJunit() throws Exception {
		RozaStatement arrayEqualsAssertion = new RozaStatement("assertArrayEquals(new Object[0], new Object[0]);");
		RozaStatement equalsAssertion = new RozaStatement("assertEquals(0, 0);");
		RozaStatement falseAssertion = new RozaStatement("assertFalse(false);");
		RozaStatement notNullAssertion = new RozaStatement("assertNotNull(null);");
		RozaStatement notSameAssertion = new RozaStatement("assertNotSame(null, null);");
		RozaStatement nullAssertion = new RozaStatement("assertNull(null);");
		RozaStatement sameAssertion = new RozaStatement("assertSame(null, null);");
		RozaStatement thatAssertion = new RozaStatement("assertThat(null, IsNull.nullValue());");
		RozaStatement trueAssertion = new RozaStatement("assertTrue(true);");
		List<RozaStatement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
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
		RozaStatement arrayEqualsAssertion = new RozaStatement("Assert.assertArrayEquals(new Object[0], new Object[0]);");
		RozaStatement equalsAssertion = new RozaStatement("Assert.assertEquals(0, 0);");
		RozaStatement falseAssertion = new RozaStatement("Assert.assertFalse(false);");
		RozaStatement notNullAssertion = new RozaStatement("Assert.assertNotNull(null);");
		RozaStatement notSameAssertion = new RozaStatement("Assert.assertNotSame(null, null);");
		RozaStatement nullAssertion = new RozaStatement("Assert.assertNull(null);");
		RozaStatement sameAssertion = new RozaStatement("Assert.assertSame(null, null);");
		RozaStatement thatAssertion = new RozaStatement("Assert.assertThat(null, IsNull.nullValue());");
		RozaStatement trueAssertion = new RozaStatement("Assert.assertTrue(true);");
		List<RozaStatement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
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