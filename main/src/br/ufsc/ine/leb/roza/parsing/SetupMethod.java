package br.ufsc.ine.leb.roza.parsing;

import java.util.List;

public class SetupMethod {

	private String name;
	private List<RozaStatement> statements;

	public SetupMethod(String name, List<RozaStatement> statements) {
		this.name = name;
		this.statements = statements;
	}

	public String getName() {
		return name;
	}

	public List<RozaStatement> getStatements() {
		return statements;
	}

	@Override
	public String toString() {
		return String.format("@Before %s()", name);
	}

}
