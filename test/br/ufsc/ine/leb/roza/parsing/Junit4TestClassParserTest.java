package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;

public class Junit4TestClassParserTest {

	private TestClassParser parser;
	private TextFile oneMethod;
	private TextFile oneTestMethod;
	private TextFile oneTestMethodOneMethod;
	private TextFile twoTestMethods;
	private TextFile oneSetupMethodOneTestMethod;
	private TextFile oneTestMethoWithWhile;
	private TextFile oneFieldOneSetupMethodOneTestMethod;

	@BeforeEach
	void setup() {
		parser = new Junit4TestClassParser();
		oneMethod = new TextFile("OneMethod.java", "public class OneMethod { public void example() { System.out.println(0); } }");
		oneTestMethod = new TextFile("OneTestMethod.java", "public class OneTestMethod { @Test public void example() { assertEquals(0, 0); } }");
		oneTestMethodOneMethod = new TextFile("OneTestMethodOneMethod.java", "public class OneTestMethodOneMethod { public void example1() { System.out.println(0); } @Test public void example2() { assertEquals(2, 2); } }") {};
		twoTestMethods = new TextFile("TwoTestMethods.java", "public class TwoTestMethods { @Test public void example1() { assertEquals(1, 1); } @Test public void example2() { assertEquals(2, 2); } }") {};
		oneSetupMethodOneTestMethod = new TextFile("OneSetupMethodOneTestMethod.java", "public class OneSetupMethodOneTestMethod { @Before public void setup() { System.out.println(0); System.out.println(1); } @Test public void example() { assertEquals(0, 0); assertEquals(1, 1); } }") {};
		oneFieldOneSetupMethodOneTestMethod = new TextFile("OneFieldOneSetupMethodOneTestMethod.java", "public class OneFieldOneSetupMethodOneTestMethod { private Sut sut; @Before public void setup() { sut = new Sut(); sut.save(0); } @Test public void example() { assertEquals(0, 0); assertEquals(1, 1); } }") {};
		oneTestMethoWithWhile = new TextFile("OneTestMethodWithWhile.java", "public class OneTestMethodWithWhile { @Test public void example() { while (1 == 0) { System.out.println(0); } assertEquals(0, 0); } }");
	}

	@Test
	void withoutFiles() throws Exception {
		assertEquals(0, parser.parse(Arrays.asList()).size());
	}

	@Test
	void oneTestMethod() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneTestMethod));
		assertEquals(1, testClasses.size());
		assertEquals("OneTestMethod", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(0, testClasses.get(0).getSetupMethods().size());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void oneTestMethodOneMethod() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneTestMethodOneMethod));
		assertEquals(1, testClasses.size());
		assertEquals("OneTestMethodOneMethod", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(0, testClasses.get(0).getSetupMethods().size());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example2", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(2, 2);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void twoTestMethods() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(twoTestMethods));
		assertEquals(1, testClasses.size());
		assertEquals("TwoTestMethods", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(0, testClasses.get(0).getSetupMethods().size());
		assertEquals(2, testClasses.get(0).getTestMethods().size());
		assertEquals("example1", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		assertEquals("example2", testClasses.get(0).getTestMethods().get(1).getName());
		assertEquals(1, testClasses.get(0).getTestMethods().get(1).getStatements().size());
		assertEquals("assertEquals(2, 2);", testClasses.get(0).getTestMethods().get(1).getStatements().get(0).getText());
	}

	@Test
	void oneSetupMethodOneTestMethod() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneSetupMethodOneTestMethod));
		assertEquals(1, testClasses.size());
		assertEquals("OneSetupMethodOneTestMethod", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(1, testClasses.get(0).getSetupMethods().size());
		assertEquals("setup", testClasses.get(0).getSetupMethods().get(0).getName());
		assertEquals(2, testClasses.get(0).getSetupMethods().get(0).getStatements().size());
		assertEquals("System.out.println(0);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(0).getText());
		assertEquals("System.out.println(1);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(1).getText());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

	@Test
	void oneFieldOneSetupMethodOneTestMethod() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneFieldOneSetupMethodOneTestMethod));
		assertEquals(1, testClasses.size());
		assertEquals("OneFieldOneSetupMethodOneTestMethod", testClasses.get(0).getName());
		assertEquals(1, testClasses.get(0).getFields().size());
		assertEquals("Sut", testClasses.get(0).getFields().get(0).getType());
		assertEquals("sut", testClasses.get(0).getFields().get(0).getName());
		assertEquals(1, testClasses.get(0).getSetupMethods().size());
		assertEquals("setup", testClasses.get(0).getSetupMethods().get(0).getName());
		assertEquals(2, testClasses.get(0).getSetupMethods().get(0).getStatements().size());
		assertEquals("sut = new Sut();", testClasses.get(0).getSetupMethods().get(0).getStatements().get(0).getText());
		assertEquals("sut.save(0);", testClasses.get(0).getSetupMethods().get(0).getStatements().get(1).getText());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		assertEquals("assertEquals(1, 1);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

	@Test
	void oneMethod() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneMethod));
		assertEquals(0, testClasses.size());
	}

	@Test
	void oneJavaClassOneTestClass() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneMethod, oneTestMethod));
		assertEquals(1, testClasses.size());
		assertEquals("OneTestMethod", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(0, testClasses.get(0).getSetupMethods().size());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(1, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
	}

	@Test
	void oneTestMethodWithWhile() throws Exception {
		List<TestClass> testClasses = parser.parse(Arrays.asList(oneTestMethoWithWhile));
		assertEquals(1, testClasses.size());
		assertEquals("OneTestMethodWithWhile", testClasses.get(0).getName());
		assertEquals(0, testClasses.get(0).getFields().size());
		assertEquals(0, testClasses.get(0).getSetupMethods().size());
		assertEquals(1, testClasses.get(0).getTestMethods().size());
		assertEquals("example", testClasses.get(0).getTestMethods().get(0).getName());
		assertEquals(2, testClasses.get(0).getTestMethods().get(0).getStatements().size());
		assertEquals("while (1 == 0) { System.out.println(0); }", testClasses.get(0).getTestMethods().get(0).getStatements().get(0).getText());
		assertEquals("assertEquals(0, 0);", testClasses.get(0).getTestMethods().get(0).getStatements().get(1).getText());
	}

}
