package br.ufsc.ine.leb.roza.utils.comparator;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ClusterComparatorBySizeAndTestName implements Comparator<Cluster> {

	@Override
	public int compare(Cluster first, Cluster second) {
		List<TestCase> firstTests = new ArrayList<>(first.getTestCases());
		List<TestCase> secondTests = new ArrayList<>(second.getTestCases());
		Integer firstSize = firstTests.size();
		int secondSize = secondTests.size();
		if (firstSize == secondSize) {
			firstTests.sort(new TestCaseComparatorByName());
			secondTests.sort(new TestCaseComparatorByName());
			Iterator<TestCase> firstIterator = firstTests.iterator();
			Iterator<TestCase> secondIterator = secondTests.iterator();
			while (firstIterator.hasNext() && secondIterator.hasNext()) {
				int comparison = firstIterator.next().getName().compareTo(secondIterator.next().getName());
				if (comparison != 0) {
					return comparison;
				}
			}
		}
		return firstSize.compareTo(secondSize);
	}

}
