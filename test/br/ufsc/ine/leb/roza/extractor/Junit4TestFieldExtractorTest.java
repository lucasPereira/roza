package br.ufsc.ine.leb.roza.extractor;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMethod;

public class Junit4TestFieldExtractorTest {

	private TestExtractor<TestField> extractor;

	@BeforeEach
	void setup() {
		extractor = new Junit4TestFieldExtractor();
	}

	@Test
	void oneTestMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
	}

	@Test
	void oneTestMethodOneSetupMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
	}

	@Test
	void oneTestMethodWithOneAssert() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneFixture() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(1, testFields.get(0).getFields().size());
		assertEquals(fixtureStatement, testFields.get(0).getFields().get(0));
	}

	@Test
	void oneTestMethodWithOneAssertOneSetupMethodWithOneAssert() throws Exception {
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(assertStatement2));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
	}

	@Test
	void oneTestMethodWithOneFixture() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(1, testFields.get(0).getFields().size());
		assertEquals(fixtureStatement, testFields.get(0).getFields().get(0));
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneFixture() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement fixtureStatement2 = new Statement("sut(2);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(fixtureStatement1));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement2));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(2, testFields.get(0).getFields().size());
		assertEquals(fixtureStatement1, testFields.get(0).getFields().get(0));
		assertEquals(fixtureStatement2, testFields.get(0).getFields().get(1));
	}

	@Test
	void oneTestMethodWithOneFixtureOneSetupMethodWithOneAssert() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		Statement fixtureStatement = new Statement("sut(0);");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(assertStatement));
		TestMethod testMethod = new TestMethod("example", Arrays.asList(fixtureStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(1, testFields.get(0).getFields().size());
		assertEquals(fixtureStatement, testFields.get(0).getFields().get(0));
	}

	@Test
	void setupMethodWithFieldInicialization() throws Exception {
		Statement sutDeclaretionAndInicialization = new Statement("Sut sut = new Sut();");
		Statement sutInicialization = new Statement("sut = new Sut();");
		Statement saveStatement = new Statement("sut.save(0);");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(sutInicialization));
		Field field = new Field("Sut", "sut", "Sut sut");
		TestMethod testMethod = new TestMethod("test", Arrays.asList(saveStatement, assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(3, testFields.get(0).getFields().size());
		assertEquals(sutDeclaretionAndInicialization, testFields.get(0).getFields().get(0));
		assertEquals("Sut sut;", testFields.get(0).getFields().get(1).getText());
		assertEquals(saveStatement, testFields.get(0).getFields().get(2));
	}

	@Test
	void complex() throws Exception {
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
		SetupMethod classASetup1 = new SetupMethod("setup1",
				Arrays.asList(classASetup1Fixture1, classASetup1Fixture2, classASetup1Assert1, classASetup1Assert2));
		SetupMethod classASetup2 = new SetupMethod("setup2", Arrays.asList(classASetup2Fixture5));
		SetupMethod classBSetup3 = new SetupMethod("setup3", Arrays.asList(classBSetup3Fixture6));
		TestMethod classATest1 = new TestMethod("test1",
				Arrays.asList(classATest1Fixture3, classATest1Fixture4, classATest1Assert3, classATest1Assert4));
		TestMethod classATest2 = new TestMethod("test2", Arrays.asList(classATest2Assert5));
		TestMethod classBTest3 = new TestMethod("test3", Arrays.asList(classBTest3Assert6));
		TestClass classA = new TestClass("A", Arrays.asList(), Arrays.asList(classASetup1, classASetup2),
				Arrays.asList(classATest1, classATest2));
		TestClass classB = new TestClass("B", Arrays.asList(), Arrays.asList(classBSetup3), Arrays.asList(classBTest3));

		List<TestField> testFields = extractor.extract(Arrays.asList(classA, classB));

		TestField firstTestClass = testFields.get(0);

		TestField secondTestClass = testFields.get(1);

		assertEquals(2, testFields.size());

		assertEquals("A", firstTestClass.getTestClass().getName());
		assertEquals(5, firstTestClass.getFields().size());
		assertEquals(classASetup1Fixture1, firstTestClass.getFields().get(0));
		assertEquals(classASetup1Fixture2, firstTestClass.getFields().get(1));
		assertEquals(classATest1Fixture3, firstTestClass.getFields().get(2));
		assertEquals(classATest1Fixture4, firstTestClass.getFields().get(3));
		assertEquals(classASetup2Fixture5, firstTestClass.getFields().get(4));

		assertEquals("B", secondTestClass.getTestClass().getName());
		assertEquals(1, secondTestClass.getFields().size());
		assertEquals(classBSetup3Fixture6, secondTestClass.getFields().get(0));

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
		List<Statement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion,
				notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
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
		List<Statement> statements = Arrays.asList(arrayEqualsAssertion, equalsAssertion, falseAssertion,
				notNullAssertion, notSameAssertion, nullAssertion, sameAssertion, thatAssertion, trueAssertion);
		TestMethod testMethod = new TestMethod("example", statements);
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));
		List<TestField> testFields = extractor.extract(Arrays.asList(testClass));
		assertEquals(1, testFields.size());
		assertEquals("ExampleTest", testFields.get(0).getTestClass().getName());
		assertEquals(0, testFields.get(0).getFields().size());
	}

}
