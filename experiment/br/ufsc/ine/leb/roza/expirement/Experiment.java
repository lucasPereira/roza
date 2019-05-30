package br.ufsc.ine.leb.roza.expirement;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Comparator;
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
import br.ufsc.ine.leb.roza.measurer.LcsSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.measurer.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.measurer.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.measurer.report.AssessmentScoreAndTestCaseNameComparator;
import br.ufsc.ine.leb.roza.measurer.report.AssessmentTestCaseNameComparator;
import br.ufsc.ine.leb.roza.parser.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parser.TestClassParser;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class Experiment {

	private static GroundTruth groundTruth;

	public static void main(String[] args) {
		groundTruth = new GroundTruth();
		groundTruth.findInconsistences();
		new FolderUtils("experiment-results").createEmptyFolder();
		lcs();
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

	private static void lcs() {
		String fileName = "lcs.csv";
		SimilarityMeasurer measurer = new LcsSimilarityMeasurer();
		averagePrecisionRecallCurve(measurer, fileName);
		precisionRecallCurve(measurer, fileName);
		matrix(measurer, fileName);
	}

	private static void lccss() {
		String fileName = "lccss.csv";
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		averagePrecisionRecallCurve(measurer, fileName);
		precisionRecallCurve(measurer, fileName);
		matrix(measurer, fileName);
	}

	private static void simian(Integer threshold) {
		String fileName = String.format("simian-%d.csv", threshold);
		SimianConfigurations configurations = new SimianConfigurations().threshold(threshold);
		SimilarityMeasurer measurer = new SimianSimilarityMeasurer(configurations, "execution/measurer");
		precisionRecallCurve(measurer, fileName);
	}

	private static void jplag(Integer sensitivity) {
		String fileName = String.format("jplag-%d.csv", sensitivity);
		JplagConfigurations configurations = new JplagConfigurations().sensitivity(sensitivity).sources("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer(configurations);
		precisionRecallCurve(measurer, fileName);
	}

	private static void deckard(Integer minTokens, Integer stride, Double similarity) {
		String fileName = String.format("deckard-%d-%d-%.1f.csv", minTokens, stride, similarity);
		DeckardConfigurations configurations = new DeckardConfigurations().minTokens(minTokens).stride(stride).similarity(similarity).srcDir("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new DeckardSimilarityMeasurer(configurations);
		precisionRecallCurve(measurer, fileName);
	}

	private static void averagePrecisionRecallCurve(SimilarityMeasurer measurer, String fileName) {
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
		Comparator<SimilarityAssessment> scoreComparator = new AssessmentScoreAndTestCaseNameComparator();

		for (Integer recallLevel = 1; recallLevel <= 10; recallLevel++) {
			BigDecimal sumPrecision = BigDecimal.ZERO;
			for (TestCase source : testCases) {
				SimilarityReport filtered = similarityReport.selectSource(source).removeReflexives();
				similarityReport.sort(scoreComparator);
				List<SimilarityAssessment> rankingForSource = filtered.getAssessments();
				List<TestCase> relevantElementsForSource = groundTruth.getRelevanteElements(testCases, source);
				Integer elementsForRecallLevel = (int) (recallLevel / 10.0 * relevantElementsForSource.size());
				List<SimilarityAssessment> rankingForRecallLevel = rankingForSource.subList(0, elementsForRecallLevel);
				Integer relevantElements = 0;
				for (Integer index = 0; index < rankingForRecallLevel.size(); index++) {
					if (relevantElementsForSource.contains(rankingForSource.get(index).getTarget())) {
						relevantElements++;
					}
				}
				BigDecimal precision = elementsForRecallLevel > 0 ? new BigDecimal(relevantElements).divide(new BigDecimal(elementsForRecallLevel), MathContext.DECIMAL32) : BigDecimal.ONE;
				sumPrecision = sumPrecision.add(precision);
			}
			BigDecimal average = sumPrecision.divide(new BigDecimal(testCases.size()), MathContext.DECIMAL32);
			csv.addLine(recallLevel.toString(), scoreFormatter.format(average));
		}
		folderUtils.writeContetAsString(String.format("average-precision-recall-curve-%s", fileName), csv.getContent());
	}

	private static void precisionRecallCurve(SimilarityMeasurer measurer, String fileName) {
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
		Comparator<SimilarityAssessment> scoreComparator = new AssessmentScoreAndTestCaseNameComparator();

		for (TestCase source : testCases) {
			SimilarityReport filtered = similarityReport.selectSource(source).removeReflexives();
			similarityReport.sort(scoreComparator);
			List<SimilarityAssessment> rankingForSource = filtered.getAssessments();
			List<TestCase> relevantElementsForSource = groundTruth.getRelevanteElements(testCases, source);
			for (Integer recallLevel = 1; recallLevel <= 10; recallLevel++) {
				Integer elementsForRecallLevel = (int) (recallLevel / 10.0 * relevantElementsForSource.size());
				List<SimilarityAssessment> rankingForRecallLevel = rankingForSource.subList(0, elementsForRecallLevel);
				Integer relevantElements = 0;
				for (Integer index = 0; index < rankingForRecallLevel.size(); index++) {
					if (relevantElementsForSource.contains(rankingForSource.get(index).getTarget())) {
						relevantElements++;
					}
				}
				BigDecimal precision = elementsForRecallLevel > 0 ? new BigDecimal(relevantElements).divide(new BigDecimal(elementsForRecallLevel), MathContext.DECIMAL32) : BigDecimal.ONE;
				csv.addLine(source.getName(), recallLevel.toString(), scoreFormatter.format(precision));
			}
		}
		folderUtils.writeContetAsString(String.format("precision-recall-curve-%s", fileName), csv.getContent());
	}

	protected static void matrix(SimilarityMeasurer measurer, String fileName) {
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
		folderUtils.writeContetAsString(String.format("matrix-%s", fileName), csv.getContent());
	}

}
