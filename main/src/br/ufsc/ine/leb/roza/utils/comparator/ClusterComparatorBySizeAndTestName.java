package br.ufsc.ine.leb.roza.utils.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

public class ClusterComparatorBySizeAndTestName implements Comparator<Cluster> {

	@Override
	public int compare(Cluster first, Cluster second) {
		List<TestCase> firstTests = new ArrayList<>(first.getTestCases());
		List<TestCase> secondTests = new ArrayList<>(second.getTestCases());
		Integer firstSize = firstTests.size();
		Integer secondSize = secondTests.size();
		if (firstSize == secondSize) {
			Collections.sort(firstTests, new TestCaseComparatorByName());
			Collections.sort(secondTests, new TestCaseComparatorByName());
			Iterator<TestCase> firstIterator = firstTests.iterator();
			Iterator<TestCase> secondIterator = secondTests.iterator();
			while (firstIterator.hasNext() && secondIterator.hasNext()) {
				Integer comparinson = firstIterator.next().getName().compareTo(secondIterator.next().getName());
				if (comparinson != 0) {
					return comparinson;
				}
			}
		}
		return firstSize.compareTo(secondSize);
	}

}
