package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

import br.ufsc.ine.leb.roza.loading.TextFile;

public class Junit4TestClassParser implements TestClassParser {

	private JavaTestClassParser parser;

	public Junit4TestClassParser() {
		parser = new JavaTestClassParser(org.junit.Before.class, org.junit.Test.class);
	}

	@Override
	public List<TestClass> parse(List<TextFile> files) {
		return parser.parse(files);
	}

}
