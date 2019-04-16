
package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
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

public class UniformityTest {
	private SimilarityMeasurer<TestField> measurer;
	private TestCaseMaterializer<TestField> materializer;

	@BeforeEach
	void setup() {
		materializer = new ManyTestCasePerClassTestCaseMaterializer("execution/materializer");
		measurer = new LongoUniformityMeasurer();
	}

	@Test
	public void sameClassWithAField() throws Exception {
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
	}

	@Test
	public void zero() throws Exception {
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList(new Statement("Sut sut1 = new Sut();")));
		SetupMethod setupMethodClass2 = new SetupMethod("setup", Arrays.asList(new Statement("Sut sut2 = new Sut();")));
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		TestClass testClass2 = new TestClass("ExampleTest2", Arrays.asList(), Arrays.asList(setupMethodClass2),
				Arrays.asList(testMethod));
		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass, testClass2));
		MaterializationReport<TestField> materializations = materializer.materialize(testsFields);
		SimilarityReport<TestField> report = measurer.measure(materializations);
		assertEquals(BigDecimal.ZERO, report.getScore());
		assertEquals(2, report.getAssessments().size());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(0).getScore());
		assertEquals(testsFields.get(0), report.getAssessments().get(0).getSource());
		assertEquals(testsFields.get(1), report.getAssessments().get(0).getTarget());
		assertEquals(BigDecimal.ZERO, report.getAssessments().get(1).getScore());
		assertEquals(testsFields.get(1), report.getAssessments().get(1).getSource());
		assertEquals(testsFields.get(0), report.getAssessments().get(1).getTarget());
	}

	@Test
	public void fifty() throws Exception {
		SetupMethod setupMethod = new SetupMethod("setup",
				Arrays.asList(new Statement("Sut sut1 = new Sut();"), new Statement("Sut sut3 = new Sut();")));
		SetupMethod setupMethodClass2 = new SetupMethod("setup",
				Arrays.asList(new Statement("Sut sut1 = new Sut();"), new Statement("Sut sut = new Sut();")));
		TestMethod testMethod = new TestMethod("test", Arrays.asList());
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		TestClass testClass2 = new TestClass("ExampleTest2", Arrays.asList(), Arrays.asList(setupMethodClass2),
				Arrays.asList(testMethod));
		Junit4TestFieldExtractor extractor = new Junit4TestFieldExtractor();
		List<TestField> testsFields = extractor.extract(Arrays.asList(testClass, testClass2));
		MaterializationReport<TestField> materializations = materializer.materialize(testsFields);
		SimilarityReport<TestField> report = measurer.measure(materializations);
		assertEquals(new BigDecimal(0.5), report.getScore());
		assertEquals(2, report.getAssessments().size());
		assertEquals(new BigDecimal(0.5), report.getAssessments().get(0).getScore());
		assertEquals(testsFields.get(0), report.getAssessments().get(0).getSource());
		assertEquals(testsFields.get(1), report.getAssessments().get(0).getTarget());
		assertEquals(new BigDecimal(0.5), report.getAssessments().get(1).getScore());
		assertEquals(testsFields.get(1), report.getAssessments().get(1).getSource());
		assertEquals(testsFields.get(0), report.getAssessments().get(1).getTarget());
	}

	@Test
	public void sameClassWithoutField() throws Exception {
		Statement assertStatement = new Statement("assertEquals(0, sut.get(0));");
		SetupMethod setupMethod = new SetupMethod("setup", Arrays.asList());
		TestMethod testMethod = new TestMethod("test", Arrays.asList(assertStatement));
		TestClass testClass = new TestClass("ExampleTest", Arrays.asList(), Arrays.asList(setupMethod),
				Arrays.asList(testMethod));
		TestClass testClass2 = new TestClass("ExampleTest2", Arrays.asList(), Arrays.asList(setupMethod),
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
	}

	@Test
	public void empty() throws Exception {
		MaterializationReport<TestField> materializations = materializer.materialize(Collections.emptyList());
		SimilarityReport<TestField> report = measurer.measure(materializations);
		report.removeReflexiveAssessments();
		assertEquals(BigDecimal.ONE, report.getScore());
		assertEquals(0, report.getAssessments().size());
	}

}
