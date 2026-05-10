package br.ufsc.ine.leb.roza.expt.c;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.MaterializationReport;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.TestCase;
import br.ufsc.ine.leb.roza.core.TestClass;
import br.ufsc.ine.leb.roza.core.TextFile;
import br.ufsc.ine.leb.roza.core.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.clustering.AnyClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.AverageLinkage;
import br.ufsc.ine.leb.roza.core.clustering.BiggestClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.CompleteLinkage;
import br.ufsc.ine.leb.roza.core.clustering.ComposedReferee;
import br.ufsc.ine.leb.roza.core.clustering.Linkage;
import br.ufsc.ine.leb.roza.core.clustering.Referee;
import br.ufsc.ine.leb.roza.core.clustering.SimilarityBasedCriterion;
import br.ufsc.ine.leb.roza.core.clustering.SingleLinkage;
import br.ufsc.ine.leb.roza.core.clustering.SmallestClusterReferee;
import br.ufsc.ine.leb.roza.core.clustering.TestCaseClusterer;
import br.ufsc.ine.leb.roza.core.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.core.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.loading.RecursiveTextFileLoader;
import br.ufsc.ine.leb.roza.core.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.core.materialization.Junit4WithAssertionsTestCaseMaterializer;
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
import br.ufsc.ine.leb.roza.core.utils.DuplicatedStatementCounter;
import br.ufsc.ine.leb.roza.core.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.utils.RozaLogger;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Experiment {

	public static void main(String[] args) {
		new FolderUtils("output/materializer").createEmptyFolder();
		TextFileLoader loader = new RecursiveTextFileLoader("src/expt/resources/c");
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		TestClassParser parser = new Junit4TestClassParser();
		TestCaseExtractor extractor = new Junit4TestCaseExtractor();
		TestCaseMaterializer materializer = new Junit4WithAssertionsTestCaseMaterializer("output/materializer");
		SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
		List<TextFile> files = loader.load();
		List<TextFile> selected = selector.select(files);
		List<TestClass> classes = parser.parse(selected);
		List<TestCase> tests = extractor.extract(classes);
		MaterializationReport materialization = materializer.materialize(tests);
		SimilarityReport report = measurer.measure(materialization);

		FolderUtils resultsFolder = new FolderUtils("experiment-results/c");
		resultsFolder.createEmptyFolder();
		DuplicatedStatementCounter.StatementCount duplicateCount = new DuplicatedStatementCounter().count(classes);
		int statementCount = duplicateCount.getStatementCount();
		int uniqueDuplicateCount = duplicateCount.getUniqueDuplicateCount();
		int totalDuplicateCount = duplicateCount.getTotalDuplicateCount();
		CommaSeparatedValues initial = new CommaSeparatedValues();
		initial.addLine("Test Classes", "Tests", "Statements", "Unique Duplicates", "Total Duplicates");
		initial.addLine(classes.size(), tests.size(), statementCount, uniqueDuplicateCount, totalDuplicateCount);
		resultsFolder.writeContetAsString("original.csv", initial.getContent());

		List<Linkage> linkages = createLinkages(report);
		List<Referee> referees = createReferees();
		List<ThresholdCriterion> criteria = createThresholdCriteria();
		execute(linkages, referees, criteria, report, resultsFolder);
	}

	private static List<Linkage> createLinkages(SimilarityReport report) {
		SingleLinkage single = new SingleLinkage(report);
		CompleteLinkage complete = new CompleteLinkage(report);
		AverageLinkage average = new AverageLinkage(report);
		return List.of(single, complete, average);
	}

	private static List<Referee> createReferees() {
		ComposedReferee biggest = new ComposedReferee(new BiggestClusterReferee(), new AnyClusterReferee());
		ComposedReferee smallest = new ComposedReferee(new SmallestClusterReferee(), new AnyClusterReferee());
		return List.of(biggest, smallest);
	}

	private static List<ThresholdCriterion> createThresholdCriteria() {
		List<ThresholdCriterion> criteria = new LinkedList<>();
		addSimilarityBasedCriterion(criteria);
		return criteria;
	}

	private static void addSimilarityBasedCriterion(List<ThresholdCriterion> criteria) {
		List<BigDecimal> degrees = List.of(new BigDecimal("0.1"),
				new BigDecimal("0.2"),
				new BigDecimal("0.3"),
				new BigDecimal("0.4"),
				new BigDecimal("0.5"),
				new BigDecimal("0.6"),
				new BigDecimal("0.7"),
				new BigDecimal("0.8"),
				new BigDecimal("0.9"),
				new BigDecimal("1.0")
		);
		degrees.forEach(degree -> {
			ThresholdCriterion similarity = new SimilarityBasedCriterion(degree);
			criteria.add(similarity);
		});
	}

	private static void execute(List<Linkage> linkages, List<Referee> referees, List<ThresholdCriterion> criteria, SimilarityReport report, FolderUtils resultsFolder) {
		CommaSeparatedValues results = new CommaSeparatedValues();
		results.addLine("Linkage", "Referee", "Similarity Threshold", "Test Classes", "Statements", "Unique Duplicates", "Total Duplicates");
		for (Linkage linkage : linkages) {
			for (Referee referee : referees) {
				for (ThresholdCriterion criterion : criteria) {
					TestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion);
					Set<Cluster> clusters = clusterer.cluster(report);
					TestClassNamingStrategy namingStrategy = new IncrementalTestClassNamingStrategy();
					ClusterRefactor refactor = new SimpleClusterRefactor(namingStrategy);
					List<TestClass> refactoredClassses = refactor.refactor(clusters);
					DuplicatedStatementCounter.StatementCount duplicateCount = new DuplicatedStatementCounter().count(refactoredClassses);
					int statementCount = duplicateCount.getStatementCount();
					int uniqueDuplicateCount = duplicateCount.getUniqueDuplicateCount();
					int totalDuplicateCount = duplicateCount.getTotalDuplicateCount();
					results.addLine(linkage, referee, criterion, clusters.size(), statementCount, uniqueDuplicateCount, totalDuplicateCount);
					RozaLogger.getInstance().info(String.format("%s %s %s %s %s %s %s", linkage, referee, criterion, clusters.size(), statementCount, uniqueDuplicateCount, totalDuplicateCount));
				}
			}
		}
		resultsFolder.writeContetAsString("refactored.csv", results.getContent());
	}

}
