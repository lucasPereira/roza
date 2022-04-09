package br.ufsc.ine.leb.roza.extraction;

import java.util.List;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class TestCase {

	private static Integer count = 0;

	private Integer id;
	private String name;
	private List<RozaStatement> fixtures;
	private List<RozaStatement> asserts;

	public TestCase(String name, List<RozaStatement> fixtures, List<RozaStatement> asserts) {
		this.id = ++count;
		this.name = name;
		this.fixtures = fixtures;
		this.asserts = asserts;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<RozaStatement> getFixtures() {
		return fixtures;
	}

	public List<RozaStatement> getAsserts() {
		return asserts;
	}

	@Override
	public String toString() {
		return String.format("%s (%d)", name, id);
	}

}
