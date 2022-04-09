package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

class ClusterJoiner {

	private Referee referee;
	private Linkage linkage;

	public ClusterJoiner(Linkage linkage, Referee referee) {
		this.linkage = linkage;
		this.referee = referee;
	}

	public WinnerCombination join(ClustersToMerge clusters) {
		Set<Combination> chosen = new HashSet<>();
		BigDecimal nearestDistance = BigDecimal.ZERO;
		Set<Combination> combinations = clusters.getCombinations();
		for (Combination combination : combinations) {
			Cluster first = combination.getFirst();
			Cluster second = combination.getSecond();
			BigDecimal distance = linkage.evaluate(first, second);
			if (distance.compareTo(nearestDistance) == 0) {
				chosen.add(combination);
			} else if (distance.compareTo(nearestDistance) > 0) {
				chosen.clear();
				chosen.add(combination);
				nearestDistance = distance;
			}
		}
		Combination winner = referee.untie(chosen);
		return new WinnerCombination(winner, nearestDistance);
	}

}
