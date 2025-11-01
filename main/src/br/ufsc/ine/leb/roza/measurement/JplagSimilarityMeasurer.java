package br.ufsc.ine.leb.roza.measurement;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.configuration.jplag.JplagConfigurations;
import br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.measurement.matrix.jplag.JplagMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.jplag.JplagMatrixValueFactory;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class JplagSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	private final JplagConfigurations configurations;

	public JplagSimilarityMeasurer(JplagConfigurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materialization = materializationReport.getMaterialization();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new JplagMatrixElementToKeyConverter();
		MatrixValueFactory<TestCaseMaterialization, BigDecimal> factory = new JplagMatrixValueFactory();
		Matrix<TestCaseMaterialization, String, BigDecimal> matrix = new Matrix<>(materialization, converter, factory);
		run();
		parse(matrix);
		for (MatrixPair<TestCaseMaterialization, BigDecimal> pair : matrix.getNonReflexivePairs()) {
			TestCase source = pair.getSource().getTestCase();
			TestCase target = pair.getTarget().getTestCase();
			BigDecimal evaluation = pair.getValue();
			builder.add(source, target, evaluation);
		}
		return builder.build();
	}

	private void parse(Matrix<TestCaseMaterialization, String, BigDecimal> matrix) {
		try {
			List<File> results = new FolderUtils(configurations.results()).listFilesRecursively("match[0-9]+-top.html");
			for (File result : results) {
				Document document = Jsoup.parse(result, "utf-8");
				Elements tables = document.body().getElementsByTag("table");
				Element table = tables.get(0);
				Elements allTableBody = table.getElementsByTag("tbody");
				Element tableBody = allTableBody.get(0);
				Elements rows = tableBody.getElementsByTag("tr");
				Element row = rows.get(0);
				Elements columns = row.getElementsByTag("th");
				Element columnFirst = columns.get(1);
				Element columnSecond = columns.get(2);
				String nameFirst = columnFirst.text().split(" ")[0];
				String nameSecond = columnSecond.text().split(" ")[0];
				String textScoreFirst = columnFirst.text().split(" ")[1].replaceAll("[%()]", "");
				String textScoreSecond = columnSecond.text().split(" ")[1].replaceAll("[%()]", "");
				BigDecimal scoreFirst = new BigDecimal(textScoreFirst).divide(new BigDecimal(100), MathContext.DECIMAL32);
				BigDecimal scoreSecond = new BigDecimal(textScoreSecond).divide(new BigDecimal(100), MathContext.DECIMAL32);
				scoreFirst = scoreFirst.compareTo(BigDecimal.ONE) == 0 ? BigDecimal.ONE : scoreFirst;
				scoreSecond = scoreSecond.compareTo(BigDecimal.ONE) == 0 ? BigDecimal.ONE : scoreSecond;
				matrix.set(nameFirst, nameSecond, scoreFirst);
				matrix.set(nameSecond, nameFirst, scoreSecond);
			}
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void run() {
		ProcessUtils processUtils = new ProcessUtils(true, true, true, true);
		List<String> arguments = new LinkedList<>();
		arguments.add("java");
		arguments.add("-jar");
		arguments.add("main/tools/jplag/jplag-2.11.9.jar");
		arguments.addAll(configurations.getAllAsArguments());
		processUtils.execute(arguments);
	}

}
