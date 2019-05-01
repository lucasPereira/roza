package br.ufsc.ine.leb.roza;

import java.util.List;

public class TestCase {

	private String name;
	private List<Statement> fixtures;
	private List<Statement> asserts;

	public TestCase(String name, List<Statement> fixtures, List<Statement> asserts) {
		this.name = name;
		this.fixtures = fixtures;
		this.asserts = asserts;
	}

	public String getName() {
		return name;
	}

	public List<Statement> getFixtures() {
		return fixtures;
	}

	public List<Statement> getAsserts() {
		return asserts;
	}

	@Override
	public String toString() {
		return name;
	}

}
