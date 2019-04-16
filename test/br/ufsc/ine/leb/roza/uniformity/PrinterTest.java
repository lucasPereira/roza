package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.extractor.Junit4TestFieldExtractor;
import br.ufsc.ine.leb.roza.materializer.ManyTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurer.LongoUniformityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.print.Printer;

public class PrinterTest {
	private SimilarityMeasurer<TestField> measurer;
	private TestCaseMaterializer<TestField> materializer;

	@BeforeEach
	void setup() {
		materializer = new ManyTestCasePerClassTestCaseMaterializer("execution/materializer");
		measurer = new LongoUniformityMeasurer();
	}

	@Test
	public void testName() throws Exception {
		Field field = new Field("Sut", "sut");
		Statement inicializationStatement = new Statement("sut = new Sut();");
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(inicializationStatement));
		TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(field), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		TestClass testClass2 = new TestClass("ExampleTest2", Arrays.asList(field), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass, testClass2));
		MaterializationReport<TestField> materializations = materializer.materialize(testsFields);
		SimilarityReport<TestField> report = measurer.measure(materializations);
		report.removeReflexiveAssessments();
		assertEquals(BigDecimal.ONE, report.getScore());
		assertEquals(2, report.getAssessments().size());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(0).getScore());
		assertEquals(testsFields.get(0), report.getAssessments().get(0).getSource());
		assertEquals(testsFields.get(1), report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ONE, report.getAssessments().get(1).getScore());
		assertEquals(testsFields.get(1), report.getAssessments().get(1).getSource());
		assertEquals(testsFields.get(0), report.getAssessments().get(1).getTarget());
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		PrintStream out = new PrintStream(bytes);
		new Printer(report).print(out);
		assertEquals("score:1\n" + "ExampleTest	ExampleTest2	1\n" + "ExampleTest2	ExampleTest	1",
				bytes.toString());
	}

}
