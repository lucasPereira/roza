package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

public class TestClassTest {

	@Test
	void withOneTestMethod() throws Exception {
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(), Arrays.asList(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(0, testClass.getSetupMethods().size());
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodAndOneSetupMethod() throws Exception {
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList());
		TestMethod testMethod = new TestMethod("example", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));

		assertEquals("ExampleTest", testClass.getName());
		assertEquals(0, testClass.getFields().size());
		assertEquals(1, testClass.getSetupMethods().size());
		assertEquals(setupMethod, testClass.getSetupMethods().get(0));
		assertEquals(1, testClass.getTestMethods().size());
		assertEquals(testMethod, testClass.getTestMethods().get(0));
	}

	@Test
	void withOneTestMethodOneSetupMethodAndOneField() throws Exception {
		Statement inicializationStatement = new Statement("sut = new Sut();");
		Statement saveStatement = new Statement("sut.save(0);");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		Field field = new Field("Sut", "sut", "private Sut sut;");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(inicializationStatement));
		TestMethod testMethod = new TestMethod("test", Arrays.asList(saveStatement, assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		assertEquals("ExampleTest", testClass.getName());
		assertEquals(1, testClass.getFields().size());
		assertEquals(field, testClass.getFields().get(0));
		assertEquals(1, testClass.getSetupMethods().size());
		assertEquals(setupMethod, testClass.getSetupMethods().get(0));
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
