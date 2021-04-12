package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.clustering.DendogramTestCaseClusterer;
import br.ufsc.ine.leb.roza.clustering.Level;
import br.ufsc.ine.leb.roza.clustering.LinkageFactory;
import br.ufsc.ine.leb.roza.clustering.Referee;
import br.ufsc.ine.leb.roza.clustering.ThresholdCriteria;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.ProgramaticTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.materialization.Junit4WithoutAssertionsTestCaseMaterializer;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterializer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class Manager {

	private TestClassParser parser;
	private TestCaseExtractor extractor;
	private SimilarityMeasurer measurer;

	private List<TestClass> testClasses;
	private List<TestCase> testCases;
	private SimilarityReport similarityReport;
	private LinkageFactory linkageFactory;
	private Referee referee;
	private ThresholdCriteria threshold;

	public Manager() {}

	public List<TestClass> loadClasses(List<File> files) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		TextFileLoader loader = new ProgramaticTextFileLoader(files);
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
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		new FolderUtils("main/exec/measurer").createEmptyFolder();
		TestCaseMaterializer materializer = new Junit4WithoutAssertionsTestCaseMaterializer("main/exec/materializer");
		MaterializationReport materializationReport = materializer.materialize(testCases);
		similarityReport = measurer.measure(materializationReport);
		return similarityReport;
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

	public void setThresholdCriteria(ThresholdCriteria theshold) {
		this.threshold = theshold;
	}

	public List<Level> distributeTests() {
		DendogramTestCaseClusterer clustering = new DendogramTestCaseClusterer(linkageFactory.create(similarityReport), referee, threshold);
		return clustering.generateLevels(similarityReport);
	}

}
