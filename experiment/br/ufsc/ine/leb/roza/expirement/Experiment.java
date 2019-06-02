package br.ufsc.ine.leb.roza.expirement;

import java.math.BigDecimal;
import java.math.MathContext;
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
import br.ufsc.ine.leb.roza.retrieval.PrecisionRecall;
import br.ufsc.ine.leb.roza.retrieval.RecallLevel;
import br.ufsc.ine.leb.roza.retrieval.StandardRecallLevels;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;

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
		for (Integer threshold = 2; threshold <= 15; threshold++) {
			simian(threshold);
		}
	}

	protected static void jplag() {
		for (Integer sensitivity = 1; sensitivity <= 50; sensitivity++) {
			jplag(sensitivity);
		}
	}

	protected static void deckard() {
		List<Integer> strides = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, Integer.MAX_VALUE);
		List<Double> similarities = Arrays.asList(0.9, 1.0);
		for (Integer minTokens = 1; minTokens < 15; minTokens++) {
			for (Integer stride : strides) {
				for (Double similarity : similarities) {
					deckard(minTokens, stride, similarity);
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

		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources");
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new JunitTestCaseExtractor(Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao"));
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("execution/materializer");
		List<TextFile> files = loader.load();
		List<TestClass> testClasses = parser.parse(files);
		List<TestCase> testCases = extractor.extract(testClasses);
		MaterializationReport materializationReport = materializer.materialize(testCases);
		SimilarityReport similarityReport = measurer.measure(materializationReport);

		averagePrecisionRecallCurve(testCases, similarityReport, fileName);
		precisionRecallCurve(testCases, similarityReport, fileName);
		matrix(similarityReport, fileName);
	}

	protected static void averagePrecisionRecallCurve(List<TestCase> testCases, SimilarityReport similarityReport, String fileName) {
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("experiment-results/average-precision-recall-curve");
		Comparator<SimilarityAssessment> scoreComparator = new AssessmentScoreAndTestCaseNameComparator();
		StandardRecallLevels standardRecallLevels = new StandardRecallLevels();
		csv.addLine("Precisão/Revocação", fileName);
		for (RecallLevel recallLevel : standardRecallLevels) {
			BigDecimal totalPrecision = BigDecimal.ZERO;
			for (TestCase source : testCases) {
				List<TestCase> ranking = similarityReport.selectSource(source).removeReflexives().sort(scoreComparator).getTargets();
				List<TestCase> relevantSet = groundTruth.getRelevanteElements(testCases, source);
				PrecisionRecall<TestCase> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
				BigDecimal precisionAtRecallLevel = precisionRecall.precisionAtRecallLevel(recallLevel);
				totalPrecision = totalPrecision.add(precisionAtRecallLevel);
			}
			BigDecimal averagePrecision = totalPrecision.divide(new BigDecimal(testCases.size()), MathContext.DECIMAL32);
			String recallLevelText = formatterUtils.recallLevel(recallLevel);
			String averagePrecisionText = formatterUtils.bigDecimal(averagePrecision);
			csv.addLine(recallLevelText, averagePrecisionText);
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	protected static void precisionRecallCurve(List<TestCase> testCases, SimilarityReport similarityReport, String fileName) {
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("experiment-results/precision-recall-curve");
		Comparator<SimilarityAssessment> scoreComparator = new AssessmentScoreAndTestCaseNameComparator();
		StandardRecallLevels standardRecallLevels = new StandardRecallLevels();
		for (TestCase source : testCases) {
			List<TestCase> ranking = similarityReport.selectSource(source).removeReflexives().sort(scoreComparator).getTargets();
			List<TestCase> relevantSet = groundTruth.getRelevanteElements(testCases, source);
			PrecisionRecall<TestCase> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
			for (RecallLevel recallLevel : standardRecallLevels) {
				BigDecimal precisionAtRecallLevel = precisionRecall.precisionAtRecallLevel(recallLevel);
				String sourceName = source.getName();
				String recallLevelText = formatterUtils.recallLevel(recallLevel);
				String precisionText = formatterUtils.bigDecimal(precisionAtRecallLevel);
				csv.addLine(sourceName, recallLevelText, precisionText);
			}
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	protected static void matrix(SimilarityReport similarityReport, String fileName) {
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("experiment-results/matrix");
		Comparator<SimilarityAssessment> nameComparator = new AssessmentTestCaseNameComparator();
		similarityReport.sort(nameComparator);
		List<SimilarityAssessment> assessments = similarityReport.getAssessments();
		for (SimilarityAssessment assessment : assessments) {
			String sourceName = assessment.getSource().getName();
			String targetName = assessment.getTarget().getName();
			String score = formatterUtils.bigDecimal(assessment.getScore());
			csv.addLine(sourceName, targetName, score);
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

}
