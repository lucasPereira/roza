package br.ufsc.ine.leb.roza.expt.e;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.clustering.Linkage;
import br.ufsc.ine.leb.roza.clustering.Referee;
import br.ufsc.ine.leb.roza.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.refactoring.ClusterRefactor;
import br.ufsc.ine.leb.roza.refactoring.IncrementalTestClassNamingStrategy;
import br.ufsc.ine.leb.roza.refactoring.SimpleClusterRefactor;
import br.ufsc.ine.leb.roza.refactoring.TestClassNamingStrategy;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.writing.Junit4TestClassWriter;
import br.ufsc.ine.leb.roza.writing.TestClassWriter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Experiment {
	public static void main(String[] args) {
		FolderUtils materializerFolderUtils = new FolderUtils("main/exec/materializer");
		materializerFolderUtils.createEmptyFolder();

		FolderUtils resultsFolderUtils = new FolderUtils("expt/results/e");
		resultsFolderUtils.createEmptyFolder();

		TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/e");
		List<TextFile> files = loader.load();

		TextFileSelector selector = new JavaExtensionTextFileSelector();
		List<TextFile> selected = selector.select(files);

		TestClassParser parser = new Junit4TestClassParser();
		List<TestClass> classes = parser.parse(selected);

		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		List<TestCase> tests = extractor.extract(classes);

		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer(materializerFolderUtils.getBaseFolder());
		MaterializationReport materialization = materializer.materialize(tests);

		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		SimilarityReport similarityReport = measurer.measure(materialization);

		Linkage linkage = new AverageLinkage(similarityReport);
		Referee referee = new AnyClusterReferee();
		ThresholdCriterion criterion = new SimilarityBasedCriterion(BigDecimal.valueOf(0.4));
		TestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion);
		Set<Cluster> clusters = clusterer.cluster(similarityReport);

		TestClassNamingStrategy namingStrategy = new IncrementalTestClassNamingStrategy();
		ClusterRefactor refactor = new SimpleClusterRefactor(namingStrategy);
		List<TestClass> refactoredClassses = refactor.refactor(clusters);

		resultsFolderUtils.createEmptyFolder();
		TestClassWriter writer = new Junit4TestClassWriter(resultsFolderUtils.getBaseFolder());
		writer.write(refactoredClassses);

		CommaSeparatedValues similarityMatrix = new CommaSeparatedValues();
		var testNames = Stream.concat(Stream.of("Test"), tests.stream().map(TestCase::getName)).toArray();
		similarityMatrix.addLine(testNames);
		tests.forEach(source -> {
			var measurements = new LinkedList<String>();
			measurements.add(source.getName());
			tests.forEach(target -> {
				measurements.add(similarityReport.getPair(source, target).getScore().toString());
			});
			similarityMatrix.addLine(measurements.toArray());
		});
		resultsFolderUtils.writeContetAsString("similarity-matrix.csv", similarityMatrix.getContent());
	}
}
