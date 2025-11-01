package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

class TestClassTest {

	@Test
	void withOneTestMethod() {
		TestMethod testMethod = new TestMethod("example", List.of());
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(), List.of(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(0, testClass.getSetupMethods().size());
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodAndOneSetupMethod() {
		SetupMethod setupMethod = new SetupMethod("setup", List.of());
		TestMethod testMethod = new TestMethod("example", List.of());
		TestClass testClass = new TestClass("ExampleTest", List.of(), List.of(setupMethod), List.of(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(1, testClass.getSetupMethods().size());
		assertEquals(setupMethod, testClass.getSetupMethods().get(0));
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodOneSetupMethodAndOneField() {
		Statement inicializationStatement = new Statement("sut = new Sut();");
		Statement saveStatement = new Statement("sut.save(0);");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		Field field = new Field("Sut", "sut");
		SetupMethod setupMethod = new SetupMethod("setup", List.of(inicializationStatement));
		TestMethod testMethod = new TestMethod("test", List.of(saveStatement, assertStatement));
		TestClass testClass = new TestClass("ExampleTest", List.of(field), List.of(setupMethod), List.of(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(1, testClass.getFields().size());
		assertEquals(field, testClass.getFields().get(0));
		assertEquals(1, testClass.getSetupMethods().size());
		assertEquals(setupMethod, testClass.getSetupMethods().get(0));
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withoutTestMethods() {
		assertThrows(NoTestMethodException.class, () -> new TestClass("ExampleTest", List.of(), List.of(), List.of()));
	}

}
