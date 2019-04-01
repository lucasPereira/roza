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
import br.ufsc.ine.leb.roza.utils.ProcessUtils;

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
		ProcessUtils processUtils = new ProcessUtils(true, false);
		String tool = "tools/simian/tool/simian-2.5.10.jar";
		String threshold = "-threshold=2";
		String sourceFiles = getSourceFiles(materializations).toString();
		File fileReport = new File(resultsFolder, "report.xml");
		processUtils.execute(fileReport, "java", "-jar", tool, "-formatter=xml", threshold, "-language=java", sourceFiles);
	}

	private StringBuilder getSourceFiles(List<TestCaseMaterialization> materializations) {
		StringBuilder files = new StringBuilder();
		for (TestCaseMaterialization materialization : materializations) {
			files.append(materialization.getFilePath());
			files.append(" ");
		}
		return files;
	}

}
