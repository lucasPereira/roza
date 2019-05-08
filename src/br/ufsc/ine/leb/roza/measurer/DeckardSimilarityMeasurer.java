package br.ufsc.ine.leb.roza.measurer;

import java.io.File;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.support.intersector.Intersector;
import br.ufsc.ine.leb.roza.support.matrix.Matrix;
import br.ufsc.ine.leb.roza.support.matrix.MatrixElementToKeyConverter;
import br.ufsc.ine.leb.roza.support.matrix.MatrixPair;
import br.ufsc.ine.leb.roza.support.matrix.MatrixValueFactory;
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

	private void parse(Matrix<TestCaseMaterialization, String, Intersector> matrix, List<TestCaseMaterialization> materializations) {}

	private void run(MaterializationReport materializationReport) {
		FolderUtils folderUtils = new FolderUtils("tools/deckard");
		String argumentsText = configurations.getAllAsArguments().stream().collect(Collectors.joining(System.lineSeparator()));
		String argumentsScript = String.format("#!/bin/sh\n%s", argumentsText);
		folderUtils.writeContetAsString("config", argumentsScript);
		ProcessUtils processUtils = new ProcessUtils(true, true, false);
		processUtils.execute(new File("tools/deckard"), "./tool/scripts/clonedetect/deckard.sh");
	}

}
