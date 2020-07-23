package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TextFile;
import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.TestCaseExtractor;
import br.ufsc.ine.leb.roza.loading.ProgramaticTextFileLoader;
import br.ufsc.ine.leb.roza.loading.TextFileLoader;
import br.ufsc.ine.leb.roza.measurement.LccssSimilarityMeasurer;
import br.ufsc.ine.leb.roza.measurement.SimilarityMeasurer;
import br.ufsc.ine.leb.roza.parsing.Junit4TestClassParser;
import br.ufsc.ine.leb.roza.parsing.TestClassParser;
import br.ufsc.ine.leb.roza.selection.JavaExtensionTextFileSelector;
import br.ufsc.ine.leb.roza.selection.TextFileSelector;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class Manager {

	private List<TestClass> classes;
	private TestClassParser parser;
	private TestCaseExtractor extractor;
	private SimilarityMeasurer measurer;

	public Manager() {
		parser = new Junit4TestClassParser();
		extractor = new Junit4TestCaseExtractor();
		measurer = new LccssSimilarityMeasurer();
	}

	public List<TestClass> loadClasses(List<File> files) {
		new FolderUtils("main/exec/materializer").createEmptyFolder();
		TextFileLoader loader = new ProgramaticTextFileLoader(files);
		TextFileSelector selector = new JavaExtensionTextFileSelector();
		List<TextFile> textFiles = loader.load();
		List<TextFile> seletedTextFiles = selector.select(textFiles);
		classes = parser.parse(seletedTextFiles);
		return classes;
	}

	public List<TestClass> getTestClasses() {
		return classes;
	}

	public List<TestCase> extractTestCases(List<TestClass> classes) {
		return extractor.extract(classes);
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

}
