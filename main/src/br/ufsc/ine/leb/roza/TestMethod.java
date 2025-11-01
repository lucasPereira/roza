package br.ufsc.ine.leb.roza;

import java.util.List;

public class TestMethod {

	private final String name;
	private final List<Statement> statements;

	public TestMethod(String name, List<Statement> statements) {
		this.name = name;
		this.statements = statements;
	}

	public String getName() {
		return name;
	}

	public List<Statement> getStatements() {
		return statements;
	}

}
