package br.ufsc.ine.leb.roza.expirement.b;

import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;

public class Exemple {

	public static void main(String[] args) {
		CommaSeparatedValues csv = new CommaSeparatedValues();

		List<SimilarityMeasurer> measures = new LinkedList<>();
		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources/b");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new Junit5TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("execution/materializer");
		SimilarityMeasurer measurer = new LcsSimilarityMeasurer();

		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		MaterializationReport materializations = materializer.materialize(tests);
		SimilarityReport report = measurer.measure(materializations);

		for (SimilarityAssessment assessment : report.getAssessments()) {
			System.out.println(assessment.getScore());
		}
	}

}
