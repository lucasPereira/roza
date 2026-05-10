package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;
import br.ufsc.ine.leb.roza.core.legacy.MaterializationReport;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;
import br.ufsc.ine.leb.roza.core.legacy.TestCase;
import br.ufsc.ine.leb.roza.core.legacy.TestClass;
import br.ufsc.ine.leb.roza.core.legacy.TextFile;
import br.ufsc.ine.leb.roza.core.legacy.clustering.AgglomerativeHierarchicalClusteringTestCaseClusterer;
import br.ufsc.ine.leb.roza.core.legacy.clustering.Level;
import br.ufsc.ine.leb.roza.core.legacy.clustering.LinkageFactory;
import br.ufsc.ine.leb.roza.core.legacy.clustering.Referee;
import br.ufsc.ine.leb.roza.core.legacy.clustering.ThresholdCriterion;
import br.ufsc.ine.leb.roza.core.legacy.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.core.legacy.loading.ProgrammaticTextFileLoader;
import br.ufsc.ine.leb.roza.core.legacy.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.core.legacy.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.legacy.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.core.legacy.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.core.legacy.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.core.legacy.refactoring.ClusterRefactor;
import br.ufsc.ine.leb.roza.core.legacy.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.core.legacy.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.core.legacy.utils.FolderUtils;
import br.ufsc.ine.leb.roza.core.legacy.writing.Junit4TestClassWriter;
import br.ufsc.ine.leb.roza.core.legacy.writing.TestClassWriter;

public class Manager {

	private TestClassParser parser;
	private TestCaseExtractor extractor;
	private SimilarityMeasurer measurer;
	private LinkageFactory linkageFactory;
	private Referee referee;
	private ThresholdCriterion threshold;

	private List<TestClass> testClasses;
	private List<TestCase> testCases;
	private SimilarityReport similarityReport;
	private Set<Cluster> clusters;
	private List<TestClass> refactoredTestClasses;
	private ClusterRefactor refactor;

	public Manager() {}

	public List<TestClass> loadClasses(List<File> files) {
		new FolderUtils("output/materializer").createEmptyFolder();
		TextFileLoader loader = new ProgrammaticTextFileLoader(files);
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		List<TextFile> textFiles = loader.load();
		List<TextFile> seletedTextFiles = selector.select(textFiles);
		testClasses = parser.parse(seletedTextFiles);
		return testClasses;
	}

	public List<TestCase> extractTestCases() {
		testCases = extractor.extract(testClasses);
		return testCases;
	}

	public SimilarityReport measureTestCases() {
		new FolderUtils("output/materializer").createEmptyFolder();
		new FolderUtils("output/measurer").createEmptyFolder();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("output/materializer");
		MaterializationReport materializationReport = materializer.materialize(testCases);
		similarityReport = measurer.measure(materializationReport);
		return similarityReport;
	}

	public List<Level> distributeTests() {
		AgglomerativeHierarchicalClusteringTestCaseClusterer clustering = new AgglomerativeHierarchicalClusteringTestCaseClusterer(linkageFactory.create(similarityReport), referee, threshold);
		return clustering.generateLevels(similarityReport);
	}

	public List<TestClass> refactorClusters() {
		refactoredTestClasses = refactor.refactor(clusters);
		return refactoredTestClasses;
	}

	public void selectCluster(Set<Cluster> clusters) {
		this.clusters = clusters;
	}

	public void writeTestClasses(String baseFolder) {
		TestClassWriter writer = new Junit4TestClassWriter(baseFolder);
		writer.write(refactoredTestClasses);
	}

	public void setTestClassParser(TestClassParser parser) {
		this.parser = parser;
	}

	public void setTestCaseExtractor(TestCaseExtractor extractor) {
		this.extractor = extractor;
	}

	public void setSimilarityMeasurer(SimilarityMeasurer measurer) {
		this.measurer = measurer;
	}

	public void setLinkageFactory(LinkageFactory linkageFactory) {
		this.linkageFactory = linkageFactory;
	}

	public void setReferee(Referee referee) {
		this.referee = referee;
	}

	public void setThresholdCriterion(ThresholdCriterion theshold) {
		this.threshold = theshold;
	}

	public void setRefactorStrategy(ClusterRefactor refactor) { this.refactor = refactor; }

}
