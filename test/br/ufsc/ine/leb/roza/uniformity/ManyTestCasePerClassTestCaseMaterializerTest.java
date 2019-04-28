package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMaterialization;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.extractor.Junit4TestFieldExtractor;
import br.ufsc.ine.leb.roza.materializer.ManyTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class ManyTestCasePerClassTestCaseMaterializerTest {

	private TestCaseMaterializer<TestField> materializer;
	private FileUtils fileUtils;

	@BeforeEach
	void setup() {
		new FolderUtils("execution/materializer").createEmptyFolder();
		fileUtils = new FileUtils();
		materializer = new ManyTestCasePerClassTestCaseMaterializer("execution/materializer");
	}

	@Test
	void oneTestCase() throws Exception {
		Statement inicializationStatement = new Statement("Sut sut = new Sut();");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(inicializationStatement));
		TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));

		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass));
		MaterializationReport<TestField> report = materializer.materialize(testsFields);
		List<TestMaterialization<TestField>> materializations = report.getMaterializations();

		StringBuilder generatedClass = new StringBuilder();
		generatedClass.append("[sut]");
		assertEquals("execution/materializer", report.getBaseFolder());
		assertEquals(1, materializations.size());
		assertEquals(testClass, materializations.get(0).getTestBlock().getTestClass());
		assertEquals("ExampleTest_0.field", materializations.get(0).getFileName());
		assertEquals("execution/materializer/ExampleTest_0.field", materializations.get(0).getFilePath());
		assertEquals(generatedClass.toString(), fileUtils.readContetAsString(materializations.get(0).getFilePath()));
	}

	@Test
	void anField() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList());
		TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
		Field field = new Field("Sut", "sut", "Sut sut");
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));

		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass));
		MaterializationReport<TestField> report = materializer.materialize(testsFields);
		List<TestMaterialization<TestField>> materializations = report.getMaterializations();

		StringBuilder generatedClass = new StringBuilder();
		generatedClass.append("[sut]");
		assertEquals("execution/materializer", report.getBaseFolder());
		assertEquals(1, materializations.size());
		assertEquals(testClass, materializations.get(0).getTestBlock().getTestClass());
		assertEquals("ExampleTest_0.field", materializations.get(0).getFileName());
		assertEquals("execution/materializer/ExampleTest_0.field", materializations.get(0).getFilePath());
		assertEquals(generatedClass.toString(), fileUtils.readContetAsString(materializations.get(0).getFilePath()));
	}

	@Test
	void twoTestCases() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");

		SetupMethod setupMethod1 = new SetupMethod("setUp", Arrays.asList(fixtureStatement1));
		TestMethod testMethod1 = new TestMethod("test", Arrays.asList(assertStatement1));
		TestClass testClass = new TestClass("ExampleTest1", Arrays.asList(), Arrays.asList(setupMethod1),
				Arrays.asList(testMethod1));

		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		SetupMethod setupMethod2 = new SetupMethod("setUp", Arrays.asList(fixtureStatement2));
		TestMethod testMethod2 = new TestMethod("test", Arrays.asList(assertStatement2));
		TestClass testClass2 = new TestClass("ExampleTest2", Arrays.asList(), Arrays.asList(setupMethod2),
				Arrays.asList(testMethod2));
		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass, testClass2));
		MaterializationReport<TestField> report = materializer.materialize(testsFields);
		List<TestMaterialization<TestField>> materializations = report.getMaterializations();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("[]");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("[]");

		assertEquals("execution/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(testsFields.get(0), materializations.get(0).getTestBlock());
		assertEquals(testsFields.get(1), materializations.get(1).getTestBlock());
		assertEquals("ExampleTest1_0.field", materializations.get(0).getFileName());
		assertEquals("ExampleTest2_1.field", materializations.get(1).getFileName());
		assertEquals("execution/materializer/ExampleTest1_0.field", materializations.get(0).getFilePath());
		assertEquals("execution/materializer/ExampleTest2_1.field", materializations.get(1).getFilePath());
		assertEquals(generatedClass1.toString(), fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2.toString(), fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

	@Test
	void twoTestClassWithTheSameName() throws Exception {
		Statement fixtureStatement1 = new Statement("sut(1);");
		Statement assertStatement1 = new Statement("assertEquals(1, 1);");

		SetupMethod setupMethod1 = new SetupMethod("setUp", Arrays.asList(fixtureStatement1));
		TestMethod testMethod1 = new TestMethod("test", Arrays.asList(assertStatement1));
		TestClass testClass = new TestClass("ExampleTest1", Arrays.asList(), Arrays.asList(setupMethod1),
				Arrays.asList(testMethod1));

		Statement fixtureStatement2 = new Statement("sut(2);");
		Statement assertStatement2 = new Statement("assertEquals(2, 2);");
		SetupMethod setupMethod2 = new SetupMethod("setUp", Arrays.asList(fixtureStatement2));
		TestMethod testMethod2 = new TestMethod("test", Arrays.asList(assertStatement2));
		TestClass testClass2 = new TestClass("ExampleTest1", Arrays.asList(), Arrays.asList(setupMethod2),
				Arrays.asList(testMethod2));
		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass, testClass2));
		MaterializationReport<TestField> report = materializer.materialize(testsFields);
		List<TestMaterialization<TestField>> materializations = report.getMaterializations();

		StringBuilder generatedClass1 = new StringBuilder();
		generatedClass1.append("[]");

		StringBuilder generatedClass2 = new StringBuilder();
		generatedClass2.append("[]");

		assertEquals("execution/materializer", report.getBaseFolder());
		assertEquals(2, materializations.size());
		assertEquals(testsFields.get(0), materializations.get(0).getTestBlock());
		assertEquals(testsFields.get(1), materializations.get(1).getTestBlock());
		assertEquals("ExampleTest1_0.field", materializations.get(0).getFileName());
		assertEquals("ExampleTest1_1.field", materializations.get(1).getFileName());
		assertEquals("execution/materializer/ExampleTest1_0.field", materializations.get(0).getFilePath());
		assertEquals("execution/materializer/ExampleTest1_1.field", materializations.get(1).getFilePath());
		assertEquals(generatedClass1.toString(), fileUtils.readContetAsString(materializations.get(0).getFilePath()));
		assertEquals(generatedClass2.toString(), fileUtils.readContetAsString(materializations.get(1).getFilePath()));
	}

}
