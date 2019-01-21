package br.ufsc.ine.leb.roza;

import java.util.List;

public class SetupMethod {

	private String name;
	private List<Statement> statements;

	public SetupMethod(String name, List<Statement> statements) {
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
