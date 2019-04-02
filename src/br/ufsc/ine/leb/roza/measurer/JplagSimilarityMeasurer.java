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

public class JplagSimilarityMeasurer implements SimilarityMeasurer {

	private String resultsFolder;

	public JplagSimilarityMeasurer(String resultsFolder) {
		this.resultsFolder = resultsFolder;
	}

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		Map<TestCase, Map<TestCase, BigDecimal>> scores = new HashMap<>();
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		if (materializations.size() > 1) {
			run(materializationReport);
			scores = parseReport(materializations);
		}
		List<SimilarityAssessment> assesssments = new LinkedList<>();
		for (TestCaseMaterialization source : materializations) {
			for (TestCaseMaterialization target : materializations) {
				TestCase sourceTestCase = source.getTestCase();
				TestCase targetTestCase = target.getTestCase();
				BigDecimal score = evaluateScore(scores, sourceTestCase, targetTestCase);
				SimilarityAssessment assessment = new SimilarityAssessment(sourceTestCase, targetTestCase, score);
				assesssments.add(assessment);
			}
		}
		return new SimilarityReport(assesssments);
	}

	private Map<TestCase, Map<TestCase, BigDecimal>> parseReport(List<TestCaseMaterialization> materializations) {
		try {
			Map<String, TestCase> testCases = new HashMap<>();
			Map<TestCase, Map<TestCase, BigDecimal>> scores = new HashMap<>();
			for (TestCaseMaterialization materialization : materializations) {
				testCases.put(materialization.getFileName(), materialization.getTestCase());
				scores.put(materialization.getTestCase(), new HashMap<>());
			}
			Document document = Jsoup.parse(new File(resultsFolder, "index.html"), "utf-8");
			Elements tables = document.body().getElementsByTag("table");
			Element table = tables.get(2);
			Elements rows = table.getElementsByTag("tr");
			for (Integer rowIndex = 0; rowIndex < rows.size(); rowIndex++) {
				Element row = rows.get(0);
				Elements columns = row.getElementsByTag("td");
				Element source = columns.get(0);
				String sourceName = source.text();
				for (Integer columnIndex = 2; columnIndex < columns.size(); columnIndex++) {
					Element target = columns.get(columnIndex);
					String targetName = target.getElementsByTag("a").get(0).text();
					String textScore = target.getElementsByTag("font").get(0).text().replaceAll("[^0-9]", "");
					BigDecimal score = new BigDecimal(textScore).divide(new BigDecimal(1000));
					TestCase targetTestCase = testCases.get(targetName);
					TestCase sourceTestCase = testCases.get(sourceName);
					scores.get(sourceTestCase).put(targetTestCase, score);
					scores.get(targetTestCase).put(sourceTestCase, score);
				}
			}
			return scores;
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

	private BigDecimal evaluateScore(Map<TestCase, Map<TestCase, BigDecimal>> scores, TestCase source, TestCase target) {
		if (source.equals(target)) {
			return BigDecimal.ONE;
		} else if (scores.containsKey(source) && scores.get(source).containsKey(target)) {
			return scores.get(source).get(target);
		} else {
			return BigDecimal.ZERO;
		}
	}

	private void run(MaterializationReport materializationReport) {
		ProcessUtils processUtils = new ProcessUtils(true, true, true);
		String tool = "tools/jplag/tool/jplag-2.11.9.jar";
		String sensitivity = "5";
		String sourceFolder = materializationReport.getBaseFolder();
		String logFile = new File(resultsFolder, "log.txt").getPath();
		processUtils.execute("java", "-jar", tool, "-vlpd", "-l", "java17", "-m", "0%", "-t", sensitivity, "-s", sourceFolder, "-r", resultsFolder, "-o", logFile);
	}

}
