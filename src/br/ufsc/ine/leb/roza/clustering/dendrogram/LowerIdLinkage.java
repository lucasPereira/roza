package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.TestCase;

class LowerIdLinkage implements Linkage {

	@Override
	public Combination link(ClustersToMerge clusters) {
		List<Combination> combinations = new ArrayList<>(clusters.getCombinations());
		Collections.sort(combinations, new Comparator<Combination>() {

			@Override
			public int compare(Combination combination1, Combination combination2) {
				Iterator<Integer> ids1 = ids(combination1).iterator();
				Iterator<Integer> ids2 = ids(combination2).iterator();
				while (ids1.hasNext() && ids2.hasNext()) {
					int comparation = ids1.next().compareTo(ids2.next());
					if (comparation != 0) {
						return comparation;
					}
				}
				return ids1.hasNext() ? 1 : ids2.hasNext() ? -1 : 0;
			}

			private List<Integer> ids(Combination combination) {
				List<TestCase> tests = new ArrayList<>(combination.getFirst().getTestCases());
				tests.addAll(combination.getSecond().getTestCases());
				return tests.stream().map(test -> test.getId()).sorted().collect(Collectors.toList());
			}

		});
		return combinations.iterator().next();
	}

}
