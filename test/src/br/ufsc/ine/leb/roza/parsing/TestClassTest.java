package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;
import br.ufsc.ine.leb.roza.fixtures.ParsingFixtures;

class TestClassTest {

	@Test
	void withOneTestMethod() throws Exception {
		TestMethod testMethod = ParsingFixtures.buildEmptyTestMethod();
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(0, testClass.getSetupMethods().size());
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodAndOneSetupMethod() throws Exception {
		SetupMethod setupMethod = ParsingFixtures.buildEmptySetupMethod();
		TestMethod testMethod = ParsingFixtures.buildEmptyTestMethod();
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod), Arrays.asList(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(1, testClass.getSetupMethods().size());
		assertEquals(setupMethod, testClass.getSetupMethods().get(0));
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodAndOneField() throws Exception {
		Field field = ParsingFixtures.buildGenericField();
		TestMethod testMethod = ParsingFixtures.buildEmptyTestMethod();
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(), Arrays.asList(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(1, testClass.getFields().size());
		assertEquals(field, testClass.getFields().get(0));
		assertEquals(0, testClass.getSetupMethods().size());
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withoutTestMethods() throws Exception {
		assertThrows(NoTestMethodException.class, () -> {
			new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList());
		});
	}

}
