package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClassSelectorTest {

	private TestClassSelector selector;

	@BeforeEach
	void setup() {
		selector = new Junit4TestClassSelector();
	}

	@Test
	void withoutFiles() throws Exception {
		assertEquals(0, selector.select(Arrays.asList()).size());
	}

	@Test
	void oneTestMethod() throws Exception {
		TextFile exampleTestDotJava = new TextFile("ExampleTest.java", "public class ExampleTest { @Test public void example() { assertEquals(0, 0); } }");
		List<TestClass> selection = selector.select(Arrays.asList(exampleTestDotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest", selection.get(0).getName());
		assertEquals(1, selection.get(0).getTestMethods().size());
		assertEquals("example", selection.get(0).getTestMethods().get(0).getName());
	}

	@Test
	void oneTestMethodOneMethod() throws Exception {
		TextFile exampleTestDotJava = new TextFile("ExampleTest.java", "public class ExampleTest { public void example1() { System.out.println(0); } @Test public void example2() { assertEquals(0, 0); } }") {};
		List<TestClass> selection = selector.select(Arrays.asList(exampleTestDotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest", selection.get(0).getName());
		assertEquals(1, selection.get(0).getTestMethods().size());
		assertEquals("example2", selection.get(0).getTestMethods().get(0).getName());
	}

	@Test
	void twoTestMethods() throws Exception {
		TextFile exampleTestDotJava = new TextFile("ExampleTest.java", "public class ExampleTest { @Test public void example1() { assertEquals(1, 1); } @Test public void example2() { assertEquals(2, 2); } }") {};
		List<TestClass> selection = selector.select(Arrays.asList(exampleTestDotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest", selection.get(0).getName());
		assertEquals(2, selection.get(0).getTestMethods().size());
		assertEquals("example1", selection.get(0).getTestMethods().get(0).getName());
		assertEquals("example2", selection.get(0).getTestMethods().get(1).getName());
	}

}
