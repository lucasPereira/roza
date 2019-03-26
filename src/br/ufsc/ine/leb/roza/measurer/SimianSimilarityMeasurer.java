package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.materializer.OneTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;

public class SimianSimilarityMeasurer implements SimilarityMeasurer {

	private TestCaseMaterializer materializer;
	private String generatedSourceFolder;
	private String resultsFolder;

	public SimianSimilarityMeasurer(String generatedSourceFolder, String resultsFolder) {
		this.generatedSourceFolder = generatedSourceFolder;
		this.resultsFolder = resultsFolder;
		materializer = new OneTestCasePerClassTestCaseMaterializer(generatedSourceFolder);
	}

	@Override
	public SimilarityReport measure(List<TestCase> testCases) {
		materializer.materialize(testCases);
		run();
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (TestCase testCase : testCases) {
			SimilarityAssessment assessment = new SimilarityAssessment(testCase, testCase, BigDecimal.ONE);
			assessments.add(assessment);
		}
		return new SimilarityReport(assessments);
	}

	private void run() {
		try {
			ProcessBuilder builder = new ProcessBuilder();
			String tool = "tools/simian/tool/simian-2.5.10.jar";
			String files = new File(generatedSourceFolder, "*.java").getAbsolutePath();
			File report = new File(resultsFolder, "report.xml");
			builder.command("java", "-jar", tool, "-formatter=xml", "-threshold=2", "-language=java", files);
			builder.redirectOutput(report);
			Process process = builder.start();
			process.waitFor();
		} catch (Exception exception) {}
	}

}
