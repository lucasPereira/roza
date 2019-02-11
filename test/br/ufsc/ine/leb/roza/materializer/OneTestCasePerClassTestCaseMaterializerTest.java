package br.ufsc.ine.leb.roza.materializer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.loader.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loader.TextFileLoader;
import br.ufsc.ine.leb.roza.utils.FileUtils;

public class OneTestCasePerClassTestCaseMaterializerTest {

	private TestCaseMaterializer materializer;
	private TextFileLoader loader;

	@BeforeEach
	void setup() {
		materializer = new OneTestCasePerClassTestCaseMaterializer("test-resources/materializer");
		loader = new RecursiveTextFileLoader("test-resources/materializer");
		new FileUtils().createEmptyFolder("test-resources/materializer");
	}

	@Test
	void oneTestCase() throws Exception {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestCase testCase = new TestCase("example", Arrays.asList(fixtureStatement), Arrays.asList(assertStatement));
		materializer.materialize(Arrays.asList(testCase));
		List<TextFile> files = loader.load();

		StringBuilder generatedClass = new StringBuilder();
		generatedClass.append("public class TestClass1ExampleTest {\n\n");
		generatedClass.append("\t@Test()\n");
		generatedClass.append("\tpublic void example() {\n");
		generatedClass.append("\t\tsut(0);\n");
		generatedClass.append("\t\tassertEquals(0, 0);\n");
		generatedClass.append("\t}\n");
		generatedClass.append("}\n");

		assertEquals(1, files.size());
		assertEquals("TestClass1ExampleTest.java", files.get(0).getName());
		assertEquals(generatedClass.toString(), files.get(0).getContent());
	}

	@Test
	void twoTetCases() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example1", Arrays.asList(fixtureStatement1), Arrays.asList(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example2", Arrays.asList(fixtureStatement2), Arrays.asList(assertStatement2));
		materializer.materialize(Arrays.asList(testCase1, testCase2));
		List<TextFile> files = loader.load();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("public class TestClass1Example1Test {\n\n");
		generatedClass1.append("\t@Test()\n");
		generatedClass1.append("\tpublic void example1() {\n");
		generatedClass1.append("\t\tsut(1);\n");
		generatedClass1.append("\t\tassertEquals(1, 1);\n");
		generatedClass1.append("\t}\n");
		generatedClass1.append("}\n");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("public class TestClass2Example2Test {\n\n");
		generatedClass2.append("\t@Test()\n");
		generatedClass2.append("\tpublic void example2() {\n");
		generatedClass2.append("\t\tsut(2);\n");
		generatedClass2.append("\t\tassertEquals(2, 2);\n");
		generatedClass2.append("\t}\n");
		generatedClass2.append("}\n");

		assertEquals(2, files.size());
		assertEquals("TestClass1Example1Test.java", files.get(0).getName());
		assertEquals("TestClass2Example2Test.java", files.get(1).getName());
		assertEquals(generatedClass1.toString(), files.get(0).getContent());
		assertEquals(generatedClass2.toString(), files.get(1).getContent());
	}

	@Test
	void twoTetCasesWithTheSameName() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example", Arrays.asList(fixtureStatement1), Arrays.asList(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example", Arrays.asList(fixtureStatement2), Arrays.asList(assertStatement2));
		materializer.materialize(Arrays.asList(testCase1, testCase2));
		List<TextFile> files = loader.load();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("public class TestClass1ExampleTest {\n\n");
		generatedClass1.append("\t@Test()\n");
		generatedClass1.append("\tpublic void example() {\n");
		generatedClass1.append("\t\tsut(1);\n");
		generatedClass1.append("\t\tassertEquals(1, 1);\n");
		generatedClass1.append("\t}\n");
		generatedClass1.append("}\n");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("public class TestClass2ExampleTest {\n\n");
		generatedClass2.append("\t@Test()\n");
		generatedClass2.append("\tpublic void example() {\n");
		generatedClass2.append("\t\tsut(2);\n");
		generatedClass2.append("\t\tassertEquals(2, 2);\n");
		generatedClass2.append("\t}\n");
		generatedClass2.append("}\n");

		assertEquals(2, files.size());
		assertEquals("TestClass1ExampleTest.java", files.get(0).getName());
		assertEquals("TestClass2ExampleTest.java", files.get(1).getName());
		assertEquals(generatedClass1.toString(), files.get(0).getContent());
		assertEquals(generatedClass2.toString(), files.get(1).getContent());
	}

}
