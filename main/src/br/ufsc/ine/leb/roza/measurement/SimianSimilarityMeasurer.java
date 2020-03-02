package br.ufsc.ine.leb.roza.measurement;

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
import br.ufsc.ine.leb.roza.measurement.configuration.simian.SimianConfigurations;
import br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.simian.SimianMatrixValueFactory;
import br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class SimianSimilarityMeasurer implements SimilarityMeasurer {

	private SimianConfigurations configurations;
	private String resultsFolder;

	public SimianSimilarityMeasurer(SimianConfigurations configurations, String resultsFolder) {
		this.configurations = configurations;
		this.resultsFolder = resultsFolder;
	}

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new SimianMatrixElementToKeyConverter();
		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new SimianMatrixValueFactory();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);
		if (materializations.size() > 1) {
			File fileReport = new File(resultsFolder, "report.xml");
			run(materializationReport, fileReport);
			parse(matrix, fileReport);
		}
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (MatrixPair<TestCaseMaterialization, Intersector> pair : matrix.getPairs()) {
			TestCase source = pair.getSource().getTestCase();
			TestCase target = pair.getTarget().getTestCase();
			Intersector intersector = pair.getValue();
			BigDecimal evaluation = intersector.evaluate();
			SimilarityAssessment assessment = new SimilarityAssessment(source, target, evaluation);
			assessments.add(assessment);
		}
		return new SimilarityReport(assessments);
	}

	private void parse(Matrix<TestCaseMaterialization, String, Intersector> matrix, File fileReport) {
		try {
			Document document = Jsoup.parse(fileReport, "utf-8");
			Elements sets = document.getElementsByTag("set");
			for (Integer setIndex = 0; setIndex < sets.size(); setIndex++) {
				Element set = sets.get(setIndex);
				Elements blocks = set.getElementsByTag("block");
				for (Integer sourceBlockIndex = 0; sourceBlockIndex < blocks.size(); sourceBlockIndex++) {
					Element sourceBlock = blocks.get(sourceBlockIndex);
					String sourceKey = sourceBlock.attr("sourceFile");
					Integer start = Integer.parseInt(sourceBlock.attr("startLineNumber"));
					Integer end = Integer.parseInt(sourceBlock.attr("endLineNumber"));
					for (Integer targetBlockIndex = 0; targetBlockIndex < blocks.size(); targetBlockIndex++) {
						Element targetBlock = blocks.get(targetBlockIndex);
						String targetKey = targetBlock.attr("sourceFile");
						matrix.get(sourceKey, targetKey).addSegment(start, end);
					}
				}
			}
		} catch (IOException excecao) {
			throw new RuntimeException(excecao);
		}
	}

	private void run(MaterializationReport materializationReport, File fileReport) {
		ProcessUtils processUtils = new ProcessUtils(true, false, true, false);
		List<String> arguments = new LinkedList<String>();
		arguments.add("java");
		arguments.add("-jar");
		arguments.add("main/tools/simian/simian-2.5.10.jar");
		arguments.addAll(configurations.getAllAsArguments());
		arguments.add(new File(materializationReport.getBaseFolder(), "*.java").getPath());
		processUtils.execute(fileReport,arguments);
	}

}
