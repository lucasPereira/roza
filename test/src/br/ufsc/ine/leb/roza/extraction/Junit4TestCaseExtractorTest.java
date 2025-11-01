package br.ufsc.ine.leb.roza.extraction;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Junit4TestCaseExtractorTest {

	private TestCaseExtractor extractor;

	@BeforeEach
	void setup() {
		extractor = new Junit4TestCaseExtractor();
	}

	@Test
	void oneTestMethod() {
		TestMethod testMethod = new TestMethod("example", List.of());
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodOneSetupMethod() {
		TestMethod testMethod = new TestMethod("example", List.of());
		SetupMethod setupMethod = new SetupMethod("setup", List.of());
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneAssert() {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestMethod testMethod = new TestMethod("example", List.of(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneFixture() {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(fixtureStatement));
		TestMethod testMethod = new TestMethod("example", List.of(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneAssert() {
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(assertStatement1));
		TestMethod testMethod = new TestMethod("example", List.of(assertStatement2));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(0, testCases.get(0).getFixtures().size());
		assertEquals(2, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement1, testCases.get(0).getAsserts().get(0));
		assertEquals(assertStatement2, testCases.get(0).getAsserts().get(1));
	}

	@Test
	void oneTestMethodWithOneFixture() {
		Statement fixtureStatement = new Statement("sut(0);");
		TestMethod testMethod = new TestMethod("example", List.of(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneFixture() {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement fixtureStatement2 = new Statement("sut(2);");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(fixtureStatement1));
		TestMethod testMethod = new TestMethod("example", List.of(fixtureStatement2));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(2, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement1, testCases.get(0).getFixtures().get(0));
		assertEquals(fixtureStatement2, testCases.get(0).getFixtures().get(1));
		assertEquals(0, testCases.get(0).getAsserts().size());
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneAssert() {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		Statement fixtureStatement = new Statement("sut(0);");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(assertStatement));
		TestMethod testMethod = new TestMethod("example", List.of(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
		assertEquals(1, testCases.size());
		assertEquals("example", testCases.get(0).getName());
		assertEquals(1, testCases.get(0).getFixtures().size());
		assertEquals(fixtureStatement, testCases.get(0).getFixtures().get(0));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void setupMethodWithFieldInicialization() {
		Statement sutDeclaretionAndInicialization = new Statement("Sut sut = new Sut();");
		Statement sutInicialization = new Statement("sut = new Sut();");
		Statement saveStatement = new Statement("sut.save(0);");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(sutInicialization));
		Field field = new Field("Sut", "sut");
		TestMethod testMethod = new TestMethod("test", List.of(saveStatement, assertStatement));
		TestClass testClass = new TestClass("", List.of(field), List.of(setupMethod), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));

		assertEquals(1, testCases.size());
		assertEquals("test", testCases.get(0).getName());
		assertEquals(2, testCases.get(0).getFixtures().size());
		assertEquals(sutDeclaretionAndInicialization, testCases.get(0).getFixtures().get(0));
		assertEquals(saveStatement, testCases.get(0).getFixtures().get(1));
		assertEquals(1, testCases.get(0).getAsserts().size());
		assertEquals(assertStatement, testCases.get(0).getAsserts().get(0));
	}

	@Test
	void complex() {
		Statement classASetup1Fixture1 = new Statement("sut(1);");
		Statement classASetup1Fixture2 = new Statement("sut(2);");
		Statement classATest1Fixture3 = new Statement("sut(3);");
		Statement classATest1Fixture4 = new Statement("sut(4);");
		Statement classASetup2Fixture5 = new Statement("sut(5);");
		Statement classBSetup3Fixture6 = new Statement("sut(6);");
		Statement classASetup1Assert1 = new Statement("assertEquals(1, 1);");
		Statement classASetup1Assert2 = new Statement("assertEquals(2, 2);");
		Statement classATest1Assert3 = new Statement("assertEquals(3, 3);");
		Statement classATest1Assert4 = new Statement("assertEquals(4, 4);");
		Statement classATest2Assert5 = new Statement("assertEquals(5, 5);");
		Statement classBTest3Assert6 = new Statement("assertEquals(6, 6);");
		SetupMethod classASetup1 = new SetupMethod("setup1", List.of(classASetup1Fixture1, classASetup1Fixture2, classASetup1Assert1, classASetup1Assert2));
		SetupMethod classASetup2 = new SetupMethod("setup2", List.of(classASetup2Fixture5));
		SetupMethod classBSetup3 = new SetupMethod("setup3", List.of(classBSetup3Fixture6));
		TestMethod classATest1 = new TestMethod("test1", List.of(classATest1Fixture3, classATest1Fixture4, classATest1Assert3, classATest1Assert4));
		TestMethod classATest2 = new TestMethod("test2", List.of(classATest2Assert5));
		TestMethod classBTest3 = new TestMethod("test3", List.of(classBTest3Assert6));
		TestClass classA = new TestClass("A", List.of(), List.of(classASetup1, classASetup2), List.of(classATest1, classATest2));
		TestClass classB = new TestClass("B", List.of(), List.of(classBSetup3), List.of(classBTest3));

		List<TestCase> testCases = extractor.extract(List.of(classA, classB));
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
	void oneTestMethodWithAllAssertsOfJunit() {
		Statement arrayEqualsAssertion = new Statement("assertArrayEquals(new Object[0], new Object[0]);");
		Statement equalsAssertion = new Statement("assertEquals(0, 0);");
		Statement falseAssertion = new Statement("assertFalse(false);");
		Statement notNullAssertion = new Statement("assertNotNull(null);");
		Statement notSameAssertion = new Statement("assertNotSame(null, null);");
		Statement nullAssertion = new Statement("assertNull(null);");
		Statement sameAssertion = new Statement("assertSame(null, null);");
		Statement thatAssertion = new Statement("assertThat(null, IsNull.nullValue());");
		Statement trueAssertion = new Statement("assertTrue(true);");
		List<Statement> statements = List.of(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
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
	void oneTestMethodWithAllNonStaticAssertsOfJunit() {
		Statement arrayEqualsAssertion = new Statement("Assert.assertArrayEquals(new Object[0], new Object[0]);");
		Statement equalsAssertion = new Statement("Assert.assertEquals(0, 0);");
		Statement falseAssertion = new Statement("Assert.assertFalse(false);");
		Statement notNullAssertion = new Statement("Assert.assertNotNull(null);");
		Statement notSameAssertion = new Statement("Assert.assertNotSame(null, null);");
		Statement nullAssertion = new Statement("Assert.assertNull(null);");
		Statement sameAssertion = new Statement("Assert.assertSame(null, null);");
		Statement thatAssertion = new Statement("Assert.assertThat(null, IsNull.nullValue());");
		Statement trueAssertion = new Statement("Assert.assertTrue(true);");
		List<Statement> statements = List.of(arrayEqualsAssertion, equalsAssertion, falseAssertion, notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));
		List<TestCase> testCases = extractor.extract(List.of(testClass));
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
