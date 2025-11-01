package br.ufsc.ine.leb.roza.expt.b;

import java.util.Comparator;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.DeckardSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.measurement.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;

public class Examples {

	public static void main(String[] args) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		FolderUtils folderUtils = new FolderUtils("expt/results/b");
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
		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/b");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");

		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		return materializer.materialize(tests);
	}

	private static void includeMetric(CommaSeparatedValues csv, MaterializationReport materialization, String metric, SimilarityMeasurer measurer) {
		new FolderUtils("main/exec/measurer").createEmptyFolder();
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
