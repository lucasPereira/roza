package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

class ClusterJoiner {

	private Referee referee;
	private Linkage linkage;

	public ClusterJoiner(Linkage linkage, Referee referee) {
		this.linkage = linkage;
		this.referee = referee;
	}

	public Combination join(ClustersToMerge clusters) {
		Set<Combination> choosed = new HashSet<>();
		BigDecimal nearestDistance = BigDecimal.ZERO;
		Set<Combination> combinations = clusters.getCombinations();
		for (Combination combination : combinations) {
			Cluster first = combination.getFirst();
			Cluster second = combination.getSecond();
			BigDecimal distance = linkage.evaluate(first, second);
			if (distance.compareTo(nearestDistance) == 0) {
				choosed.add(combination);
			} else if (distance.compareTo(nearestDistance) > 0) {
				choosed.clear();
				choosed.add(combination);
				nearestDistance = distance;
			}
		}
		return choose(choosed);
	}

	private Combination choose(Set<Combination> choosed) {
		if (choosed.size() == 0) {
			throw new TiebreakException(choosed);
		} else if (choosed.size() == 1) {
			return choosed.iterator().next();
		} else {
			return untie(choosed);
		}
	}

	private Combination untie(Set<Combination> choosed) {
		Set<Combination> tiebreak = referee.untie(choosed);
		if (tiebreak.size() == 0) {
			throw new TiebreakException(tiebreak);
		} else if (tiebreak.size() == 1) {
			return tiebreak.iterator().next();
		} else {
			throw new TiebreakException(tiebreak);
		}
	}

}
