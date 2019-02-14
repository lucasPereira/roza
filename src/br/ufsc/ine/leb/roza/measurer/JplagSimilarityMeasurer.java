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

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.materializer.OneTestCasePerClassTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materializer.TestCaseMaterializer;
import jplag.ExitException;
import jplag.Program;
import jplag.options.CommandLineOptions;

public class JplagSimilarityMeasurer implements SimilarityMeasurer {

	private String gerenatedSourceFolder;
	private String resultsFolder;
	private TestCaseMaterializer materializer;

	public JplagSimilarityMeasurer(String generatedSourceFolder, String resultsFolder) {
		this.gerenatedSourceFolder = generatedSourceFolder;
		this.resultsFolder = resultsFolder;
		materializer = new OneTestCasePerClassTestCaseMaterializer(generatedSourceFolder);
	}

	@Override
	public SimilarityReport measure(List<TestCase> testCases) {
		Map<TestCase, Map<TestCase, BigDecimal>> scores = new HashMap<>();
		List<TestCaseMaterialization> materializations = materializer.materialize(testCases);
		if (testCases.size() > 1) {
			runJplag();
			Map<TestCase, Map<TestCase, BigDecimal>> jplagScores = parserJplagReport(materializations);
			scores.putAll(jplagScores);
		}
		List<SimilarityAssessment> assesssments = new LinkedList<>();
		for (TestCase source : testCases) {
			for (TestCase target : testCases) {
				BigDecimal score = evaluateScore(scores, source, target);
				SimilarityAssessment assessment = new SimilarityAssessment(source, target, score);
				assesssments.add(assessment);
			}
		}
		return new SimilarityReport(assesssments);
	}

	private Map<TestCase, Map<TestCase, BigDecimal>> parserJplagReport(List<TestCaseMaterialization> materializations) {
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

	private void runJplag() {
		try {
			String logFile = new File(resultsFolder, "log.txt").getAbsolutePath();
			String[] arguments = { "-l", "java17", "-vlpd", "-t", "5", "-m", "0%", "-s", gerenatedSourceFolder, "-r", resultsFolder, "-o", logFile };
			CommandLineOptions options = new CommandLineOptions(arguments, null);
			Program program = new Program(options);
			program.run();
		} catch (ExitException excecao) {
			throw new RuntimeException(excecao);
		}
	}

}
