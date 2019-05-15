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
import br.ufsc.ine.leb.roza.measurer.JplagSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
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
		simian(2);
		simian(3);
		simian(4);
		simian(5);
		simian(6);
		simian(7);
		simian(8);
		simian(9);
		simian(10);
		jplag(1);
		jplag(2);
		jplag(3);
		jplag(4);
		jplag(5);
		jplag(6);
		jplag(7);
		jplag(8);
		jplag(9);
		jplag(10);
		jplag(11);
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
