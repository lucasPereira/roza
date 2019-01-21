package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.exceptions.NoTestMethodException;

public class TestClassTest {

	@Test
	void withOneTestMethod() throws Exception {
		TestMethod exampleTestMethod = new TestMethod("example", Arrays.asList());
		TestClass exampleTestClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(exampleTestMethod));
		assertEquals("ExampleTest", exampleTestClass.getName());
		assertEquals(0, exampleTestClass.getSetupMethods().size());
		assertEquals(1, exampleTestClass.getTestMethods().size());
		assertEquals("example", exampleTestClass.getTestMethods().get(0).getName());
	}

	@Test
	void withOneTestMethodAndOneSetupMethod() throws Exception {
		SetupMethod exampleSetupMethod = new SetupMethod("setup", Arrays.asList());
		TestMethod exampleTestMethod = new TestMethod("example", Arrays.asList());
		TestClass exampleTestClass = new TestClass("ExampleTest", Arrays.asList(exampleSetupMethod), Arrays.asList(exampleTestMethod));
		assertEquals("ExampleTest", exampleTestClass.getName());
		assertEquals(1, exampleTestClass.getSetupMethods().size());
		assertEquals("setup", exampleTestClass.getSetupMethods().get(0).getName());
		assertEquals(1, exampleTestClass.getTestMethods().size());
		assertEquals("example", exampleTestClass.getTestMethods().get(0).getName());
	}

	@Test
	void withoutTestMethods() throws Exception {
		assertThrows(NoTestMethodException.class, () -> {
			new TestClass("ExampleTest", Arrays.asList(), Arrays.asList());
		});
	}

}
