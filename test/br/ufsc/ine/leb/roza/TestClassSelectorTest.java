package br.ufsc.ine.leb.roza;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestClassSelectorTest {

	private TestClassSelector selector;
	private TextFile exampleTest1DotJava;
	private TextFile exampleTest2DotJava;
	private TextFile exampleTest3DotJava;
	private TextFile exampleTest4DotJava;

	@BeforeEach
	void setup() {
		selector = new Junit4TestClassSelector();
		exampleTest1DotJava = new TextFile("ExampleTest1.java", "public class ExampleTest1 { @Test public void example() { assertEquals(0, 0); } }");
		exampleTest2DotJava = new TextFile("ExampleTest2.java", "public class ExampleTest2 { public void example1() { System.out.println(0); } @Test public void example2() { assertEquals(0, 0); } }") {};
		exampleTest3DotJava = new TextFile("ExampleTest3.java", "public class ExampleTest3 { @Test public void example1() { assertEquals(1, 1); } @Test public void example2() { assertEquals(2, 2); } }") {};
		exampleTest4DotJava = new TextFile("ExampleTest5.java", "public class ExampleTest4 { @Before public void setup() { System.out.println(0); } @Test public void example() { assertEquals(0, 0); } }") {};
	}

	@Test
	void withoutFiles() throws Exception {
		assertEquals(0, selector.select(Arrays.asList()).size());
	}

	@Test
	void oneTestMethod() throws Exception {
		List<TestClass> selection = selector.select(Arrays.asList(exampleTest1DotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest1", selection.get(0).getName());
		assertEquals(1, selection.get(0).getTestMethods().size());
		assertEquals("example", selection.get(0).getTestMethods().get(0).getName());
	}

	@Test
	void oneTestMethodOneMethod() throws Exception {
		List<TestClass> selection = selector.select(Arrays.asList(exampleTest2DotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest2", selection.get(0).getName());
		assertEquals(1, selection.get(0).getTestMethods().size());
		assertEquals("example2", selection.get(0).getTestMethods().get(0).getName());
	}

	@Test
	void twoTestMethods() throws Exception {
		List<TestClass> selection = selector.select(Arrays.asList(exampleTest3DotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest3", selection.get(0).getName());
		assertEquals(2, selection.get(0).getTestMethods().size());
		assertEquals("example1", selection.get(0).getTestMethods().get(0).getName());
		assertEquals("example2", selection.get(0).getTestMethods().get(1).getName());
	}

	@Test
	void oneSetupMethodOneTestMethod() throws Exception {
		List<TestClass> selection = selector.select(Arrays.asList(exampleTest4DotJava));
		assertEquals(1, selection.size());
		assertEquals("ExampleTest4", selection.get(0).getName());
		assertEquals(1, selection.get(0).getSetupMethods().size());
		assertEquals("setup", selection.get(0).getSetupMethods().get(0).getName());
		assertEquals(1, selection.get(0).getTestMethods().size());
		assertEquals("example", selection.get(0).getTestMethods().get(0).getName());
	}

}
