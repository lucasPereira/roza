package br.ufsc.ine.leb.roza.measurement;

import java.io.File;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurement.configuration.deckard.DeckardConfigurations;
import br.ufsc.ine.leb.roza.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurement.matrix.Matrix;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.measurement.matrix.MatrixValueFactory;
import br.ufsc.ine.leb.roza.measurement.matrix.deckard.DeckardMatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.measurement.matrix.deckard.DeckardMatrixValueFactory;
import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.ProcessUtils;

public class DeckardSimilarityMeasurer implements SimilarityMeasurer {

	private DeckardConfigurations configurations;

	public DeckardSimilarityMeasurer(DeckardConfigurations configurations) {
		this.configurations = configurations;
	}

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		MatrixElementToKeyConverter<TestCaseMaterialization, String> converter = new DeckardMatrixElementToKeyConverter();
		MatrixValueFactory<TestCaseMaterialization, Intersector> factory = new DeckardMatrixValueFactory();
		Matrix<TestCaseMaterialization, String, Intersector> matrix = new Matrix<>(materializations, converter, factory);
		if (materializations.size() > 1) {
			run(materializationReport);
			parse(matrix, materializations);
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

	private void parse(Matrix<TestCaseMaterialization, String, Intersector> matrix, List<TestCaseMaterialization> materializations) {
		List<File> reports = new FolderUtils(configurations.clusterDir()).listFilesRecursively("post_cluster_vdb_.*");
		for (File reportFile : reports) {
			String reportContent = new FileUtils().readContetAsString(reportFile).trim();
			String lineSeparator = System.lineSeparator();
			List<String> clusters = Arrays.asList(reportContent.split(lineSeparator + lineSeparator));
			Iterator<String> clusterIterator = clusters.stream().filter((cluster) -> {
				return !cluster.trim().isEmpty();
			}).iterator();
			while (clusterIterator.hasNext()) {
				String cluster = clusterIterator.next();
				List<String> matches = Arrays.asList(cluster.split(lineSeparator));
				for (String sourceMatch : matches) {
					List<String> sourceFields = Arrays.asList(sourceMatch.split("\\s"));
					String sourceFile = sourceFields.get(3);
					Integer sourceLine = Integer.parseInt(sourceFields.get(4).replaceFirst("LINE:([0-9]+):([0-9]+)", "$1"));
					Integer sourceLenght = Integer.parseInt(sourceFields.get(4).replaceFirst("LINE:([0-9]+):([0-9]+)", "$2"));
					for (String targetMatch : matches) {
						List<String> targetFields = Arrays.asList(targetMatch.split("\\s"));
						String targetFile = targetFields.get(3);
						matrix.get(sourceFile, targetFile).addSegment(sourceLine, sourceLine + sourceLenght - 1);
					}
				}
			}
		}
	}

	private void run(MaterializationReport materializationReport) {
		FolderUtils folderUtils = new FolderUtils("main/tools/deckard");
		String argumentsText = configurations.getAllAsArguments().stream().collect(Collectors.joining(System.lineSeparator()));
		String argumentsScript = String.format("#!/bin/sh\n%s", argumentsText);
		folderUtils.writeContetAsString("config", argumentsScript);
		ProcessUtils processUtils = new ProcessUtils(true, true, true, false);
		processUtils.execute(new File("main/tools/deckard"), "./tool/scripts/clonedetect/deckard.sh");
		folderUtils.removeFile("config");
	}

}
