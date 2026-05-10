package br.ufsc.ine.leb.roza.expt.b;

import java.util.Comparator;
import java.util.List;

import br.ufsc.ine.leb.roza.core.legacy.MaterializationReport;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityAssessment;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.TestClass;
import br.ufsc.ine.leb.roza.core.legacy.TextFile;
import br.ufsc.ine.leb.roza.core.legacy.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.legacy.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.legacy.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.core.legacy.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.core.legacy.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.legacy.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.DeckardSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.core.legacy.measurement.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.core.legacy.measurement.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.core.legacy.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.core.legacy.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.legacy.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.core.legacy.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.core.legacy.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.legacy.utils.FormatterUtils;
import br.ufsc.ine.leb.roza.core.legacy.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;

public class Examples {

	public static void main(String[] args) {
		new FolderUtils("output/materializer").createEmptyFolder();
		FolderUtils folderUtils = new FolderUtils("experiment-results/b");
		folderUtils.createEmptyFolder();
		CommaSeparatedValues csv = new CommaSeparatedValues();

		MaterializationReport materialization = getMaterializationReport();

		JplagConfigurations jplagSettings = new JplagConfigurations().sensitivity(1);
		SimianConfigurations simianSettings = new SimianConfigurations().threshold(2);
		DeckardConfigurations deckardSettings = new DeckardConfigurations(true).minTokens(1).stride(Integer.MAX_VALUE).similarity(1.0);
		includeMetric(csv, materialization, "LCS", new LcsSimilarityMeasurer());
		includeMetric(csv, materialization, "LCCSS", new LccssSimilarityMeasurer());
		includeMetric(csv, materialization, "JPlag", new JplagSimilarityMeasurer(jplagSettings));
		includeMetric(csv, materialization, "Simian", new SimianSimilarityMeasurer(simianSettings));
		includeMetric(csv, materialization, "Deckard", new DeckardSimilarityMeasurer(deckardSettings));
		folderUtils.writeContetAsString("examples.csv", csv.getContent());
	}

	private static MaterializationReport getMaterializationReport() {
		TextFileLoader loader = new RecursiveTextFileLoader("src/expt/resources/b");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("output/materializer");

		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		return materializer.materialize(tests);
	}

	private static void includeMetric(CommaSeparatedValues csv, MaterializationReport materialization, String metric, SimilarityMeasurer measurer) {
		new FolderUtils("output/measurer").createEmptyFolder();
		FormatterUtils formatterUtils = new FormatterUtils();
		Comparator<SimilarityAssessment> comparator = new SimilarityAssessmentComparatorBySourceAndTargetNames();
		SimilarityReport report = measurer.measure(materialization).sort(comparator).removeReflexives();
		for (SimilarityAssessment assessment : report.getAssessments()) {
			String source = assessment.getSource().getName();
			String target = assessment.getTarget().getName();
			String score = formatterUtils.fractionNumberForCsv(assessment.getScore());
			csv.addLine(metric, source, target, score);
		}
	}

}
