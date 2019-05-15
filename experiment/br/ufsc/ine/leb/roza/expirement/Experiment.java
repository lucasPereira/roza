package br.ufsc.ine.leb.roza.expirement;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extractor.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.extractor.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loader.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loader.TextFileLoader;
import br.ufsc.ine.leb.roza.materializer.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurer.DeckardSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.measurer.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.measurer.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.measurer.report.AssessmentTestCaseNameComparator;
import br.ufsc.ine.leb.roza.parser.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parser.TestClassParser;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class Experiment {

	public static void main(String[] args) {
		GroundTruth groundTruth = new GroundTruth();
		groundTruth.findInconsistences();
		new FolderUtils("experiment-results").createEmptyFolder();
		lccss();
		simian();
		jplag();
		deckard();
	}

	private static void simian() {
		for (Integer threshold = 2; threshold <= 10; threshold++) {
			simian(threshold);
		}
	}

	private static void jplag() {
		for (Integer sensitivity = 1; sensitivity <= 11; sensitivity++) {
			jplag(sensitivity);
		}
	}

	private static void deckard() {
		List<Integer> minTokens = Arrays.asList(1, 10, 50);
		List<Integer> strides = Arrays.asList(0, 1, Integer.MAX_VALUE);
		List<Double> similarities = Arrays.asList(0.9, 1.0);
		for (Integer minToken : minTokens) {
			for (Integer stride : strides) {
				for (Double similarity : similarities) {
					deckard(minToken, stride, similarity);
				}
			}
		}
	}

	private static void lccss() {
		String fileName = "lccss.csv";
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		measure(measurer, fileName);
	}

	private static void simian(Integer threshold) {
		String fileName = String.format("simian-%d.csv", threshold);
		SimianConfigurations configurations = new SimianConfigurations().threshold(threshold);
		SimilarityMeasurer measurer = new SimianSimilarityMeasurer(configurations, "execution/measurer");
		measure(measurer, fileName);
	}

	private static void jplag(Integer sensitivity) {
		String fileName = String.format("jplag-%d.csv", sensitivity);
		JplagConfigurations configurations = new JplagConfigurations().sensitivity(sensitivity).sources("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer(configurations);
		measure(measurer, fileName);
	}

	private static void deckard(Integer minTokens, Integer stride, Double similarity) {
		String fileName = String.format("deckard-%d-%d-%.1f.csv", minTokens, stride, similarity);
		DeckardConfigurations configurations = new DeckardConfigurations().minTokens(minTokens).stride(stride).similarity(similarity).srcDir("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new DeckardSimilarityMeasurer(configurations);
		measure(measurer, fileName);
	}

	private static void measure(SimilarityMeasurer measurer, String fileName) {
		new FolderUtils("execution/materializer").createEmptyFolder();
		new FolderUtils("execution/measurer").createEmptyFolder();

		FolderUtils folderUtils = new FolderUtils("experiment-results");
		CommaSeparatedValues csv = new CommaSeparatedValues();
		DecimalFormat scoreFormatter = new DecimalFormat();
		scoreFormatter.setMaximumFractionDigits(Integer.MAX_VALUE);

		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources");
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new JunitTestCaseExtractor(Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao"));
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("execution/materializer");

		List<TextFile> files = loader.load();
		List<TestClass> testClasses = parser.parse(files);
		List<TestCase> testCases = extractor.extract(testClasses);
		MaterializationReport materializationReport = materializer.materialize(testCases);
		SimilarityReport similarityReport = measurer.measure(materializationReport);
		similarityReport.sort(new AssessmentTestCaseNameComparator());
		List<SimilarityAssessment> assessments = similarityReport.getAssessments();

		for (SimilarityAssessment assessment : assessments) {
			String sourceName = assessment.getSource().getName();
			String targetName = assessment.getTarget().getName();
			String score = scoreFormatter.format(assessment.getScore());
			csv.addLine(sourceName, targetName, score);
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

}
