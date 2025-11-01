package br.ufsc.ine.leb.roza.expt.a;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
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
import br.ufsc.ine.leb.roza.parsing.Junit5TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.retrieval.PrecisionRecall;
import br.ufsc.ine.leb.roza.retrieval.RecallLevel;
import br.ufsc.ine.leb.roza.retrieval.StandardRecallLevels;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;
import br.ufsc.ine.leb.roza.utils.ReportUtils;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorByScoreSourceNameAndTargetName;
import br.ufsc.ine.leb.roza.utils.comparator.SimilarityAssessmentComparatorBySourceAndTargetNames;

public class Experiment {

	private static GroundTruth groundTruth;

	public static void main(String[] args) {
		groundTruth = new GroundTruth();
		groundTruth.findInconsistencies();
		new FolderUtils("expt/results/a/average-precision-recall-curve").createEmptyFolder();
		new FolderUtils("expt/results/a/precision-recall-curve").createEmptyFolder();
		new FolderUtils("expt/results/a/matrix").createEmptyFolder();
		lcs();
		lccss();
		simian();
		jplag();
		deckard();
	}

	protected static void simian() {
		for (int threshold = 2; threshold <= 15; threshold++) {
			simian(threshold);
		}
	}

	protected static void jplag() {
		for (int sensitivity = 1; sensitivity <= 50; sensitivity++) {
			jplag(sensitivity);
		}
	}

	protected static void deckard() {
		List<Integer> strides = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, Integer.MAX_VALUE);
		List<Double> similarities = List.of(0.9, 1.0);
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
		SimilarityMeasurer measurer = new SimianSimilarityMeasurer(configurations);
		evaluateMeasure(fileName, measurer);
	}

	private static void jplag(Integer sensitivity) {
		String fileName = String.format("jplag-%d.csv", sensitivity);
		JplagConfigurations configurations = new JplagConfigurations().sensitivity(sensitivity);
		SimilarityMeasurer measurer = new JplagSimilarityMeasurer(configurations);
		evaluateMeasure(fileName, measurer);
	}

	private static void deckard(Integer minTokens, Integer stride, Double similarity) {
		FormatterUtils formatterUtils = new FormatterUtils();
		String similarityAsText = formatterUtils.fractionNumberForFileName(similarity);
		String fileName = String.format("deckard-%d-%d-%s.csv", minTokens, stride, similarityAsText);
		DeckardConfigurations configurations = new DeckardConfigurations(true).minTokens(minTokens).stride(stride).similarity(similarity);
		SimilarityMeasurer measurer = new DeckardSimilarityMeasurer(configurations);
		evaluateMeasure(fileName, measurer);
	}

	private static void evaluateMeasure(String fileName, SimilarityMeasurer measurer) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();

		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/a");
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new JunitTestCaseExtractor(List.of("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao"));
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");
		List<TextFile> files = loader.load();
		List<TestClass> testClasses = parser.parse(files);
		List<TestCase> testCases = extractor.extract(testClasses);
		MaterializationReport materializationReport = materializer.materialize(testCases);
		SimilarityReport report = measurer.measure(materializationReport);

		averagePrecisionRecallCurve(testCases, report, fileName);
		precisionRecallCurve(testCases, report, fileName);
		matrix(report, fileName);
	}

	protected static void averagePrecisionRecallCurve(List<TestCase> testCases, SimilarityReport report, String fileName) {
		ReportUtils reportUtils = new ReportUtils();
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("expt/results/a/average-precision-recall-curve");
		Comparator<SimilarityAssessment> scoreComparator = new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName();
		StandardRecallLevels standardRecallLevels = new StandardRecallLevels();
		csv.addLine("Precisão/Revocação", fileName);
		for (RecallLevel recallLevel : standardRecallLevels) {
			BigDecimal totalPrecision = BigDecimal.ZERO;
			for (TestCase source : testCases) {
				List<TestCase> ranking = reportUtils.getTargets(report.selectSource(source).removeReflexives().sort(scoreComparator));
				List<TestCase> relevantSet = groundTruth.getRelevantElements(testCases, source);
				PrecisionRecall<TestCase> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
				BigDecimal precisionAtRecallLevel = precisionRecall.precisionAtRecallLevel(recallLevel);
				totalPrecision = totalPrecision.add(precisionAtRecallLevel);
			}
			BigDecimal averagePrecision = totalPrecision.divide(new BigDecimal(testCases.size()), MathContext.DECIMAL32);
			String recallLevelText = formatterUtils.recallLevel(recallLevel);
			String averagePrecisionText = formatterUtils.fractionNumberForCsv(averagePrecision);
			csv.addLine(recallLevelText, averagePrecisionText);
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	protected static void precisionRecallCurve(List<TestCase> testCases, SimilarityReport report, String fileName) {
		ReportUtils reportUtils = new ReportUtils();
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("expt/results/a/precision-recall-curve");
		Comparator<SimilarityAssessment> scoreComparator = new SimilarityAssessmentComparatorByScoreSourceNameAndTargetName();
		StandardRecallLevels standardRecallLevels = new StandardRecallLevels();
		for (TestCase source : testCases) {
			List<TestCase> ranking = reportUtils.getTargets(report.selectSource(source).removeReflexives().sort(scoreComparator));
			List<TestCase> relevantSet = groundTruth.getRelevantElements(testCases, source);
			PrecisionRecall<TestCase> precisionRecall = new PrecisionRecall<>(ranking, relevantSet);
			for (RecallLevel recallLevel : standardRecallLevels) {
				BigDecimal precisionAtRecallLevel = precisionRecall.precisionAtRecallLevel(recallLevel);
				String sourceName = source.getName();
				String recallLevelText = formatterUtils.recallLevel(recallLevel);
				String precisionText = formatterUtils.fractionNumberForCsv(precisionAtRecallLevel);
				csv.addLine(sourceName, recallLevelText, precisionText);
			}
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	protected static void matrix(SimilarityReport report, String fileName) {
		FormatterUtils formatterUtils = new FormatterUtils();
		CommaSeparatedValues csv = new CommaSeparatedValues();
		FolderUtils folderUtils = new FolderUtils("expt/results/a/matrix");
		Comparator<SimilarityAssessment> nameComparator = new SimilarityAssessmentComparatorBySourceAndTargetNames();
		List<SimilarityAssessment> assessments = report.sort(nameComparator).getAssessments();
		for (SimilarityAssessment assessment : assessments) {
			String sourceName = assessment.getSource().getName();
			String targetName = assessment.getTarget().getName();
			String score = formatterUtils.fractionNumberForCsv(assessment.getScore());
			csv.addLine(sourceName, targetName, score);
		}
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

}
