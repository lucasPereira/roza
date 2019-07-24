package br.ufsc.ine.leb.roza.measurement.report;

import java.util.Comparator;

import br.ufsc.ine.leb.roza.TestCase;

public class TestCaseNameComparator implements Comparator<TestCase> {

	@Override
	public int compare(TestCase test1, TestCase test2) {
		return test1.getName().compareTo(test2.getName());
	}

}
