package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class SimianSimilarityMeasurer implements SimilarityMeasurer {

	private String resultsFolder;

	public SimianSimilarityMeasurer(String resultsFolder) {
		this.resultsFolder = resultsFolder;
	}

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		run(materializations);
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (TestCaseMaterialization materialization : materializations) {
			TestCase source = materialization.getTestCase();
			TestCase target = materialization.getTestCase();
			SimilarityAssessment assessment = new SimilarityAssessment(source, target, BigDecimal.ONE);
			assessments.add(assessment);
		}
		return new SimilarityReport(assessments);
	}

	private void run(List<TestCaseMaterialization> materializations) {
		try {
			StringBuilder files = new StringBuilder();
			for (TestCaseMaterialization materialization : materializations) {
				files.append(materialization.getFilePath());
				files.append(" ");
			}
			ProcessBuilder builder = new ProcessBuilder();
			String tool = "tools/simian/tool/simian-2.5.10.jar";
			File report = new File(resultsFolder, "report.xml");
			builder.command("java", "-jar", tool, "-formatter=xml", "-threshold=2", "-language=java", files.toString());
			builder.redirectOutput(report);
			Process process = builder.start();
			process.waitFor();
		} catch (Exception exception) {}
	}

}
