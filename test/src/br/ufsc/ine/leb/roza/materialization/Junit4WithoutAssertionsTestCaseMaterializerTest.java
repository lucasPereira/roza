package br.ufsc.ine.leb.roza.materialization;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

class Junit4WithoutAssertionsTestCaseMaterializerTest {

	private TestCaseMaterializer materializer;
	private FileUtils fileUtils;

	@BeforeEach
	void setup() {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		fileUtils = new FileUtils();
		materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");
	}

	@Test
	void oneTestCase() {
		Statement fixtureStatement = new Statement("sut(0);");
		Statement assertStatement = new Statement("assertEquals(0, 0);");
		TestCase testCase = new TestCase("example", List.of(fixtureStatement), List.of(assertStatement));
		MaterializationReport report = materializer.materialize(List.of(testCase));
		List<TestCaseMaterialization> materializations = report.getMaterialization();

		String generatedClass = "public class TestClass1ExampleTest {\n\n" +
			"\t@Test()\n" +
			"\tpublic void example() {\n" +
			"\t\tsut(0);\n" +
			"\t}\n" +
			"}\n";

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(1, materializations.size());
		assertEquals(testCase, materializations.get(0).getTestCase());
		assertEquals(7, materializations.get(0).getLength().intValue());
		assertEquals("TestClass1ExampleTest.java", materializations.get(0).getFileName());
		assertEquals("main/exec/materializer/TestClass1ExampleTest.java", materializations.get(0).getFilePath());
		assertEquals(generatedClass, fileUtils.readContetAsString(materializations.get(0).getFilePath()));
	}

	@Test
	void twoTetCases() {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example1", List.of(fixtureStatement1), List.of(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example2", List.of(fixtureStatement2), List.of(assertStatement2));
		MaterializationReport report = materializer.materialize(List.of(testCase1, testCase2));
		List<TestCaseMaterialization> materializations = report.getMaterialization();

		String generatedClass1 = "public class TestClass1Example1Test {\n\n" +
			"\t@Test()\n" +
			"\tpublic void example1() {\n" +
			"\t\tsut(1);\n" +
			"\t}\n" +
			"}\n";

		String generatedClass2 = "public class TestClass2Example2Test {\n\n" +
			"\t@Test()\n" +
			"\tpublic void example2() {\n" +
			"\t\tsut(2);\n" +
			"\t}\n" +
			"}\n";

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(7, materializations.get(0).getLength().intValue());
		assertEquals(7, materializations.get(1).getLength().intValue());
		assertEquals(testCase1, materializations.get(0).getTestCase());
		assertEquals(testCase2, materializations.get(1).getTestCase());
		assertEquals("TestClass1Example1Test.java", materializations.get(0).getFileName());
		assertEquals("TestClass2Example2Test.java", materializations.get(1).getFileName());
		assertEquals("main/exec/materializer/TestClass1Example1Test.java", materializations.get(0).getFilePath());
		assertEquals("main/exec/materializer/TestClass2Example2Test.java", materializations.get(1).getFilePath());
		assertEquals(generatedClass1, fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2, fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

	@Test
	void twoTetCasesWithTheSameName() {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");
		TestCase testCase1 = new TestCase("example", List.of(fixtureStatement1), List.of(assertStatement1));
		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		TestCase testCase2 = new TestCase("example", List.of(fixtureStatement2), List.of(assertStatement2));
		MaterializationReport report = materializer.materialize(List.of(testCase1, testCase2));
		List<TestCaseMaterialization> materializations = report.getMaterialization();

		String generatedClass1 = "public class TestClass1ExampleTest {\n\n" +
			"\t@Test()\n" +
			"\tpublic void example() {\n" +
			"\t\tsut(1);\n" +
			"\t}\n" +
			"}\n";

		String generatedClass2 = "public class TestClass2ExampleTest {\n\n" +
			"\t@Test()\n" +
			"\tpublic void example() {\n" +
			"\t\tsut(2);\n" +
			"\t}\n" +
			"}\n";

		assertEquals("main/exec/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(7, materializations.get(0).getLength().intValue());
		assertEquals(7, materializations.get(1).getLength().intValue());
		assertEquals(testCase1, materializations.get(0).getTestCase());
		assertEquals(testCase2, materializations.get(1).getTestCase());
		assertEquals("TestClass1ExampleTest.java", materializations.get(0).getFileName());
		assertEquals("TestClass2ExampleTest.java", materializations.get(1).getFileName());
		assertEquals("main/exec/materializer/TestClass1ExampleTest.java", materializations.get(0).getFilePath());
		assertEquals("main/exec/materializer/TestClass2ExampleTest.java", materializations.get(1).getFilePath());
		assertEquals(generatedClass1, fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2, fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

}
