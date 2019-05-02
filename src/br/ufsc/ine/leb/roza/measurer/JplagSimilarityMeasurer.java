package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.support.matrix.Matrix;
import br.ufsc.ine.leb.roza.support.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.support.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.support.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.support.matrix.TestCaseMaterializationFileNameToStringConverter;
import br.ufsc.ine.leb.roza.support.matrix.TestCaseMaterializationsToBigDecimalValueFactory;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class JplagSimilarityMeasurer implements SimilarityMeasurer {

	private String resultsFolder;

	public JplagSimilarityMeasurer(String resultsFolder) {
		this.resultsFolder = resultsFolder;
	}

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new TestCaseMaterializationFileNameToStringConverter();
		MatrixValueFactory<TestCaseMaterialization, BigDecimal> factory = new TestCaseMaterializationsToBigDecimalValueFactory();
		Matrix<TestCaseMaterialization, String, BigDecimal> matrix = new Matrix<>(materializations, converter, factory);
		if (materializations.size() > 1) {
			run(materializationReport);
			parse(matrix, materializations);
		}
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (MatrixPair<TestCaseMaterialization, BigDecimal> pair : matrix.getPairs()) {
			TestCase source = pair.getSource().getTestCase();
			TestCase target = pair.getTarget().getTestCase();
			BigDecimal evaluation = pair.getValue();
			SimilarityAssessment assessment = new SimilarityAssessment(source, target, evaluation);
			assessments.add(assessment);
		}
		return new SimilarityReport(assessments);
	}

	private void parse(Matrix<TestCaseMaterialization, String, BigDecimal> matrix, List<TestCaseMaterialization> materializations) {
		try {
			List<File> results = new FolderUtils(resultsFolder).listFilesRecursively("match[0-9]+-top.html");
			for (File result : results) {
				Document document = Jsoup.parse(result, "utf-8");
				Elements tables = document.body().getElementsByTag("table");
				Element table = tables.get(0);
				Elements tableBodys = table.getElementsByTag("tbody");
				Element tableBody = tableBodys.get(0);
				Elements rows = tableBody.getElementsByTag("tr");
				Element row = rows.get(0);
				Elements columns = row.getElementsByTag("th");
				Element columnFirst = columns.get(1);
				Element columnSecond = columns.get(2);
				String nameFirst = columnFirst.text().split(" ")[0];
				String nameSecond = columnSecond.text().split(" ")[0];
				String textScoreFirst = columnFirst.text().split(" ")[1].replaceAll("[%()]", "");
				String textScoreSecond = columnSecond.text().split(" ")[1].replaceAll("[%()]", "");
				BigDecimal scoreFirst = new BigDecimal(textScoreFirst).divide(new BigDecimal(100));
				BigDecimal scoreSecond = new BigDecimal(textScoreSecond).divide(new BigDecimal(100));
				BigDecimal oneScaled = BigDecimal.ONE.setScale(1);
				BigDecimal oneNotScaled = BigDecimal.ONE;
				scoreFirst = scoreFirst.equals(oneScaled) ? oneNotScaled : scoreFirst;
				scoreSecond = scoreSecond.equals(oneScaled) ? oneNotScaled : scoreSecond;
				matrix.set(nameFirst, nameSecond, scoreFirst);
				matrix.set(nameSecond, nameFirst, scoreSecond);
			}
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

	protected void parseSymmetric(Matrix<TestCaseMaterialization, String, BigDecimal> matrix, List<TestCaseMaterialization> materializations) {
		try {
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
					matrix.set(sourceName, targetName, score);
					matrix.set(targetName, sourceName, score);
				}
			}
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
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
