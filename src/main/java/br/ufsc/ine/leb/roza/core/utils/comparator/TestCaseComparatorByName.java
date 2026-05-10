package br.ufsc.ine.leb.roza.core.utils.comparator;

import java.util.Comparator;

import br.ufsc.ine.leb.roza.core.TestCase;

public class TestCaseComparatorByName implements Comparator<TestCase> {

	@Override
	public int compare(TestCase test1, TestCase test2) {
		return test1.getName().compareTo(test2.getName());
	}

}
