package br.ufsc.ine.leb.roza.expt.e;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.MaterializationReport;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.TestClass;
import br.ufsc.ine.leb.roza.core.TextFile;
import br.ufsc.ine.leb.roza.core.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.core.clustering.Linkage;
import br.ufsc.ine.leb.roza.core.clustering.Referee;
import br.ufsc.ine.leb.roza.core.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.core.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.core.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.core.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.core.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.core.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.core.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.refactoring.ClusterRefactor;
import br.ufsc.ine.leb.roza.core.refactoring.IncrementalTestClassNamingStrategy;
import br.ufsc.ine.leb.roza.core.refactoring.SimpleClusterRefactor;
import br.ufsc.ine.leb.roza.core.refactoring.TestClassNamingStrategy;
import br.ufsc.ine.leb.roza.core.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.core.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.core.utils.CommaSeparatedValues;
import br.ufsc.ine.leb.roza.core.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.writing.Junit4TestClassWriter;
import br.ufsc.ine.leb.roza.core.writing.TestClassWriter;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Experiment {
	public static void main(String[] args) {
		FolderUtils materializerFolderUtils = new FolderUtils("output/materializer");
		materializerFolderUtils.createEmptyFolder();

		FolderUtils resultsFolderUtils = new FolderUtils("experiment-results/e");
		resultsFolderUtils.createEmptyFolder();

		TextFileLoader loader = new RecursiveTextFileLoader("src/expt/resources/e");
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
