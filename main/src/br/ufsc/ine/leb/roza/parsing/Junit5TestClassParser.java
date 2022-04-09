package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.loading.TextFile;

public class Junit5TestClassParser implements TestClassParser {

	private JavaTestClassParser parser;

	public Junit5TestClassParser() {
		parser = new JavaTestClassParser(org.junit.jupiter.api.BeforeEach.class, org.junit.jupiter.api.Test.class);
	}

	@Override
	public List<TestClass> parse(List<TextFile> files) {
		return parser.parse(files);
	}

}
