package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;

public class BiggerClusterReferee implements Referee {

	@Override
	public Set<Combination> untie(Set<Combination> elements) {
		Set<Combination> choosed = new HashSet<>();
		Integer bigger = Integer.MIN_VALUE;
		for (Combination combination : elements) {
			Cluster first = combination.getFirst();
			Cluster second = combination.getSecond();
			Integer size = first.merge(second).getTestCases().size();
			if (size.compareTo(bigger) == 0) {
				choosed.add(combination);
			} else if (size.compareTo(bigger) > 0) {
				choosed.clear();
				choosed.add(combination);
				bigger = size;
			}
		}
		return choosed;
	}

}
