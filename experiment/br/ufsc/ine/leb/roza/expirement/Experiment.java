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
import br.ufsc.ine.leb.roza.measurer.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimianSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurer.SimilarityMeasurer;
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
	}

	private static void lccss() {
		new FolderUtils("execution/materializer").createEmptyFolder();
		FolderUtils folderUtils = new FolderUtils("experiment-results");
		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources");
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new JunitTestCaseExtractor(Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao"));
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("execution/materializer");
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		CommaSeparatedValues csv = new CommaSeparatedValues();

		List<TextFile> files = loader.load();
		List<TestClass> testClasses = parser.parse(files);
		List<TestCase> testCases = extractor.extract(testClasses);
		MaterializationReport materializationReport = materializer.materialize(testCases);
		SimilarityReport similarityReport = measurer.measure(materializationReport);
		similarityReport.sort(new AssessmentTestCaseNameComparator());
		List<SimilarityAssessment> assessments = similarityReport.getAssessments();
		DecimalFormat scoreFormatter = new DecimalFormat();
		scoreFormatter.setMaximumFractionDigits(Integer.MAX_VALUE);
		for (SimilarityAssessment assessment : assessments) {
			String sourceName = assessment.getSource().getName();
			String targetName = assessment.getTarget().getName();
			String score = scoreFormatter.format(assessment.getScore());
			csv.addLine(sourceName, targetName, score);
		}
		String fileName = "lccss.csv";
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

	private static void simian(Integer threshold) {
		new FolderUtils("execution/materializer").createEmptyFolder();
		FolderUtils folderUtils = new FolderUtils("experiment-results");
		TextFileLoader loader = new RecursiveTextFileLoader("experiment-resources");
		TestClassParser parser = new Junit5TestClassParser();
		TestCaseExtractor extractor = new JunitTestCaseExtractor(Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao"));
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("execution/materializer");
		SimianConfigurations configurations = new SimianConfigurations().threshold(threshold);
		SimilarityMeasurer measurer = new SimianSimilarityMeasurer(configurations, "execution/measurer");
		CommaSeparatedValues csv = new CommaSeparatedValues();

		List<TextFile> files = loader.load();
		List<TestClass> testClasses = parser.parse(files);
		List<TestCase> testCases = extractor.extract(testClasses);
		MaterializationReport materializationReport = materializer.materialize(testCases);
		SimilarityReport similarityReport = measurer.measure(materializationReport);
		similarityReport.sort(new AssessmentTestCaseNameComparator());
		List<SimilarityAssessment> assessments = similarityReport.getAssessments();
		DecimalFormat scoreFormatter = new DecimalFormat();
		scoreFormatter.setMaximumFractionDigits(Integer.MAX_VALUE);
		for (SimilarityAssessment assessment : assessments) {
			String sourceName = assessment.getSource().getName();
			String targetName = assessment.getTarget().getName();
			String score = scoreFormatter.format(assessment.getScore());
			csv.addLine(sourceName, targetName, score);
		}
		String fileName = String.format("simian-%d.csv", threshold);
		folderUtils.writeContetAsString(fileName, csv.getContent());
	}

}
