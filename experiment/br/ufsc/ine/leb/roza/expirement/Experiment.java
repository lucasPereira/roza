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
		new FolderUtils("experiment-results/average-precision-recall-curve").createEmptyFolder();
		new FolderUtils("experiment-results/precision-recall-curve").createEmptyFolder();
		new FolderUtils("experiment-results/matrix").createEmptyFolder();
		lcs();
		lccss();
		simian();
		jplag();
		deckard();
	}

	protected static void simian() {
		for (Integer threshold = 2; threshold <= 10; threshold++) {
			simian(threshold);
		}
	}

	protected static void jplag() {
		for (Integer sensitivity = 1; sensitivity <= 11; sensitivity++) {
			jplag(sensitivity);
		}
	}

	protected static void deckard() {
		List<Integer> minTokens = Arrays.asList(1, 10, 20, 30, 40, 50, 60);
		List<Integer> strides = Arrays.asList(0, 2, 4, Integer.MAX_VALUE);
		List<Double> similarities = Arrays.asList(0.9, 1.0);
		for (Integer minToken : minTokens) {
			for (Integer stride : strides) {
				for (Double similarity : similarities) {
					deckard(minToken, stride, similarity);
				}
			}
		}
	}

	protected static void lcs() {
		String fileName = "lcs.csv";
		SimilarityMeasurer measurer = new LcsSimilarityMeasurer();
		evaluateMeasure(fileName, measurer);
	}

	protected static void lccss() {
		String fileName = "lccss.csv";
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		evaluateMeasure(fileName, measurer);
	}

	private static void simian(Integer threshold) {
		String fileName = String.format("simian-%d.csv", threshold);
		SimianConfigurations configurations = new SimianConfigurations().threshold(threshold);
		SimilarityMeasurer measurer = new SimianSimilarityMeasurer(configurations, "execution/measurer");
		evaluateMeasure(fileName, measurer);
	}

	private static void jplag(Integer sensitivity) {
		String fileName = String.format("jplag-%d.csv", sensitivity);
		JplagConfigurations configurations = new JplagConfigurations().sensitivity(sensitivity).sources("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer(configurations);
		evaluateMeasure(fileName, measurer);
	}

	private static void deckard(Integer minTokens, Integer stride, Double similarity) {
		String fileName = String.format("deckard-%d-%d-%.1f.csv", minTokens, stride, similarity);
		DeckardConfigurations configurations = new DeckardConfigurations().minTokens(minTokens).stride(stride).similarity(similarity).srcDir("execution/materializer").results("execution/measurer");
		SimilarityMeasurer measurer = new DeckardSimilarityMeasurer(configurations);
		evaluateMeasure(fileName, measurer);
	}

	private static void evaluateMeasure(String fileName, SimilarityMeasurer measurer) {
		new FolderUtils("execution/materializer").createEmptyFolder();
		new FolderUtils("execution/measurer").createEmptyFolder();
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

		averagePrecisionRecallCurve(testCases, similarityReport, scoreFormatter, fileName);
		precisionRecallCurve(testCases, similarityReport, scoreFormatter, fileName);
		matrix(similarityReport, scoreFormatter, fileName);
	}

	private static void averagePrecisionRecallCurve(List<TestCase> testCases, SimilarityReport similarityReport, DecimalFormat scoreFormatter, String fileName) {
		FolderUtils folderUtils = new FolderUtils("experiment-results/average-precision-recall-curve");
		CommaSeparatedValues csv = new CommaSeparatedValues();
		Comparator<SimilarityAssessment> scoreComparator = new AssessmentScoreAndTestCaseNameComparator();
		csv.addLine("Precisão/Revocação", fileName);
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
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	private static void precisionRecallCurve(List<TestCase> testCases, SimilarityReport similarityReport, DecimalFormat scoreFormatter, String fileName) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("experiment-results/precision-recall-curve");
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
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	protected static void matrix(SimilarityReport similarityReport, DecimalFormat scoreFormatter, String fileName) {
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("experiment-results/matrix");
		Comparator<SimilarityAssessment> nameComparator = new AssessmentTestCaseNameComparator();
		similarityReport.sort(nameComparator);
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
