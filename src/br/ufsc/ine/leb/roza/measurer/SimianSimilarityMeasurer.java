package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		if (materializations.size() > 1) {
			File fileReport = new File(resultsFolder, "report.xml");
			run(materializationReport, fileReport);
			parseReport(materializationReport, fileReport);
		}
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (TestCaseMaterialization materializationSource : materializations) {
			TestCase source = materializationSource.getTestCase();
			for (TestCaseMaterialization materializationTarget : materializations) {
				TestCase target = materializationTarget.getTestCase();
				SimilarityAssessment assessment = new SimilarityAssessment(source, target, BigDecimal.ONE);
				assessments.add(assessment);
			}
		}
		return new SimilarityReport(assessments);
	}

	private void parseReport(MaterializationReport materializationReport, File fileReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		Map<String, TestCase> testCases = new HashMap<>();
		Map<TestCase, Map<TestCase, BigDecimal>> scores = new HashMap<>();
		for (TestCaseMaterialization materialization : materializations) {
			testCases.put(materialization.getFileName(), materialization.getTestCase());
			scores.put(materialization.getTestCase(), new HashMap<>());
		}
		try {
			Document document = Jsoup.parse(fileReport, "utf-8");
			Elements sets = document.getElementsByTag("set");
			for (Integer setIndex = 0; setIndex < sets.size(); setIndex++) {
				Element set = sets.get(setIndex);
				Elements blocks = set.getElementsByTag("block");
				for (Integer blockIndex = 0; blockIndex < blocks.size(); blockIndex++) {
					Element block = blocks.get(blockIndex);
					System.out.println("----");
					System.out.println(block.attr("sourceFile"));
					System.out.println(block.attr("startLineNumber"));
					System.out.println(block.attr("endLineNumber"));
				}
			}
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}

	}

	private void run(MaterializationReport materializationReport, File fileReport) {
		ProcessUtils processUtils = new ProcessUtils(true, false, true);
		String tool = "tools/simian/tool/simian-2.5.10.jar";
		String threshold = "-threshold=2";
		String sourceFiles = new File(materializationReport.getBaseFolder(), "*.java").getPath();
		processUtils.execute(fileReport, "java", "-jar", tool, "-formatter=xml", threshold, "-language=java", sourceFiles);
	}

}
