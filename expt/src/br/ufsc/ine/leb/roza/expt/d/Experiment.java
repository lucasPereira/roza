package br.ufsc.ine.leb.roza.expt.d;

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
import br.ufsc.ine.leb.roza.utils.DuplicatedStatementCounter;
import br.ufsc.ine.leb.roza.utils.FolderUtils;
import br.ufsc.ine.leb.roza.utils.WilcoxonSignedRankTest;
import br.ufsc.ine.leb.roza.utils.WilcoxonSignedRankTest.WilcoxonTestResult;
import br.ufsc.ine.leb.roza.writing.Junit4TestClassWriter;
import br.ufsc.ine.leb.roza.writing.TestClassWriter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Experiment {
	public static void main(String[] args) {
		int originalUniqueDuplicates = 0;
		int refactoredUniqueDuplicates = 0;
		int originalTotalDuplicates = 0;
		int refactoredTotalDuplicates = 0;

		List<Integer> originalUniqueDuplicatesList = new ArrayList<>();
		List<Integer> refactoredUniqueDuplicatesList = new ArrayList<>();
		List<Integer> originalTotalDuplicatesList = new ArrayList<>();
		List<Integer> refactoredTotalDuplicatesList = new ArrayList<>();

		FolderUtils folderUtils = new FolderUtils("expt/results/d");

		CommaSeparatedValues csv = new CommaSeparatedValues();
		csv.addLine(
			"Project",
			"Original classes", "Refactored classes",
			"Original classes attributes", "Refactored classes attributes",
			"Original classes setup methods", "Refactored classes setup methods",
			"Original classes test methods", "Refactored classes test methods",
			"Original classes statements", "Refactored classes statements",
			"Original unique duplicates", "Refactored unique duplicates",
			"Original total duplicates", "Refactored total duplicates"
		);

		for (int project = 1; project <= 16; project++) {
			new FolderUtils("main/exec/materializer").createEmptyFolder();

			TextFileLoader loader = new RecursiveTextFileLoader("expt/resources/d/p" + project + "/original");
			List<TextFile> files = loader.load();

			TextFileSelector selector = new JavaExtensionTextFileSelector();
			List<TextFile> selected = selector.select(files);

			TestClassParser parser = new Junit4TestClassParser();
			List<TestClass> classes = parser.parse(selected);

			TestCaseExtractor extractor = new Junit4TestCaseExtractor();
			List<TestCase> tests = extractor.extract(classes);

			TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");
			MaterializationReport materialization = materializer.materialize(tests);

			SimilarityMeasurer measurer = new LccssSimilarityMeasurer();
			SimilarityReport report = measurer.measure(materialization);

			Linkage linkage = new AverageLinkage(report);
			Referee referee = new AnyClusterReferee();
			ThresholdCriterion criterion = new SimilarityBasedCriterion(BigDecimal.valueOf(0.4));
			TestCaseClusterer clusterer = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkage, referee, criterion);
			Set<Cluster> clusters = clusterer.cluster(report);

			TestClassNamingStrategy namingStrategy = new IncrementalTestClassNamingStrategy();
			ClusterRefactor refactor = new SimpleClusterRefactor(namingStrategy);
			List<TestClass> refactoredClassses = refactor.refactor(clusters);

			new FolderUtils("expt/resources/d/p" + project + "/refactored").createEmptyFolder();
			TestClassWriter writer = new Junit4TestClassWriter("expt/resources/d/p" + project + "/refactored");
			writer.write(refactoredClassses);

			TextFileLoader refactoredLoader = new RecursiveTextFileLoader("expt/resources/d/p" + project + "/refactored");
			List<TextFile> refactoredFiles = refactoredLoader.load();

			TextFileSelector refactoredSelector = new JavaExtensionTextFileSelector();
			List<TextFile> refactoredSelected = refactoredSelector.select(refactoredFiles);

			TestClassParser refactoredParser = new Junit4TestClassParser();
			List<TestClass> refactoredClasses = refactoredParser.parse(refactoredSelected);

			var originalDuplicateCount = new DuplicatedStatementCounter().count(classes);
			var refactoredDuplicateCount = new DuplicatedStatementCounter().count(refactoredClasses);

			int originalClassesCount = classes.size();
			int refactoredClassesCount = refactoredClasses.size();
			long originalClassesAttributesCount = classes.stream().mapToLong(testClass -> testClass.getFields().size()).sum();
			long refactoredClassesAttributesCount = refactoredClasses.stream().mapToLong(testClass -> testClass.getFields().size()).sum();
			long originalClassesSetupMethodsCount = classes.stream().mapToLong(testClass -> testClass.getSetupMethods().size()).sum();
			long refactoredClassesSetupMethodsCount = refactoredClasses.stream().mapToLong(testClass -> testClass.getSetupMethods().size()).sum();
			long originalClassesTestMethodsCount = classes.stream().mapToLong(testClass -> testClass.getTestMethods().size()).sum();
			long refactoredClassesTestMethodsCount = refactoredClasses.stream().mapToLong(testClass -> testClass.getTestMethods().size()).sum();
			int originalClassesStatementsCount = originalDuplicateCount.getStatementCount();
			int refactoredClassesStatementsCount = refactoredDuplicateCount.getStatementCount();
			int originalClassesUniqueDuplicatesCount = originalDuplicateCount.getUniqueDuplicateCount();
			int refactoredClassesUniqueDuplicatesCount = refactoredDuplicateCount.getUniqueDuplicateCount();
			int originalClassesTotalDuplicatesCount = originalDuplicateCount.getTotalDuplicateCount();
			int refactoredClassesTotalDuplicatesCount = refactoredDuplicateCount.getTotalDuplicateCount();

			refactoredUniqueDuplicates += refactoredClassesUniqueDuplicatesCount;
			originalUniqueDuplicates += originalClassesUniqueDuplicatesCount;
			refactoredTotalDuplicates += refactoredClassesTotalDuplicatesCount;
			originalTotalDuplicates += originalClassesTotalDuplicatesCount;

			originalUniqueDuplicatesList.add(originalClassesUniqueDuplicatesCount);
			refactoredUniqueDuplicatesList.add(refactoredClassesUniqueDuplicatesCount);
			originalTotalDuplicatesList.add(originalClassesTotalDuplicatesCount);
			refactoredTotalDuplicatesList.add(refactoredClassesTotalDuplicatesCount);

			System.out.println("==================");
			System.out.printf("P%d%n", project);
			System.out.println("------------------");
			System.out.printf("Original classes: %d%n", originalClassesCount);
			System.out.printf("Refactored classes: %d%n", refactoredClassesCount);
			System.out.println("------------------");
			System.out.printf("Original classes attributes: %d%n", originalClassesAttributesCount);
			System.out.printf("Refactored classes attributes: %d%n", refactoredClassesAttributesCount);
			System.out.println("------------------");
			System.out.printf("Original classes setup methods: %d%n", originalClassesSetupMethodsCount);
			System.out.printf("Refactored classes setup methods: %d%n", refactoredClassesSetupMethodsCount);
			System.out.println("------------------");
			System.out.printf("Original classes test methods: %d%n", originalClassesTestMethodsCount);
			System.out.printf("Refactored classes test methods: %d%n", refactoredClassesTestMethodsCount);
			System.out.println("------------------");
			System.out.printf("Original classes statements: %d%n", originalClassesStatementsCount);
			System.out.printf("Refactored classes statements: %d%n", refactoredClassesStatementsCount);
			System.out.println("------------------");
			System.out.printf("Original unique duplicates: %d%n", originalClassesUniqueDuplicatesCount);
			System.out.printf("Refactored unique duplicates: %d%n", refactoredClassesUniqueDuplicatesCount);
			System.out.println("------------------");
			System.out.printf("Original total duplicates: %d%n", originalClassesTotalDuplicatesCount);
			System.out.printf("Refactored total duplicates: %d%n", refactoredClassesTotalDuplicatesCount);

			csv.addLine(
				project,
				originalClassesCount, refactoredClassesCount,
				originalClassesAttributesCount, refactoredClassesAttributesCount,
				originalClassesSetupMethodsCount, refactoredClassesSetupMethodsCount,
				originalClassesTestMethodsCount, refactoredClassesTestMethodsCount,
				originalClassesStatementsCount, refactoredClassesStatementsCount,
				originalClassesUniqueDuplicatesCount, refactoredClassesUniqueDuplicatesCount,
				originalClassesTotalDuplicatesCount, refactoredClassesTotalDuplicatesCount
			);
		}

		System.out.println("==================");
		System.out.printf("Original unique duplicates: %d%n", originalUniqueDuplicates);
		System.out.printf("Refactored unique duplicates: %d%n", refactoredUniqueDuplicates);
		System.out.println("------------------");
		System.out.printf("Original total duplicates: %d%n", originalTotalDuplicates);
		System.out.printf("Refactored total duplicates: %d%n", refactoredTotalDuplicates);

		WilcoxonSignedRankTest wilcoxonTest = new WilcoxonSignedRankTest();
		WilcoxonTestResult uniqueResult = wilcoxonTest.test(originalUniqueDuplicatesList, refactoredUniqueDuplicatesList);
		WilcoxonTestResult totalResult = wilcoxonTest.test(originalTotalDuplicatesList, refactoredTotalDuplicatesList);

		System.out.println("==================");
		System.out.println("Statistical Test (Wilcoxon Signed-Rank)");
		System.out.println("------------------");
		System.out.printf("Unique duplicates - %s%n", uniqueResult);
		System.out.printf("Total duplicates - %s%n", totalResult);

		folderUtils.removeFile("results.csv");
		folderUtils.writeContetAsString("results.csv", csv.getContent());
	}
}
