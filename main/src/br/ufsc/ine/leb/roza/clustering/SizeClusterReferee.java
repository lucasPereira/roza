package br.ufsc.ine.leb.roza.clustering;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public abstract class SizeClusterReferee implements Referee {

	private Integer initialValue;

	public SizeClusterReferee(Integer initialValue) {
		this.initialValue = initialValue;
	}

	@Override
	public final Combination untie(Set<Combination> elements) {
		Set<Combination> chosen = new HashSet<>();
		Integer chosenValue = initialValue;
		for (Combination combination : elements) {
			Cluster first = combination.getFirst();
			Cluster second = combination.getSecond();
			Integer current = first.merge(second).getTestCases().size();
			Integer comparinson = compare(chosenValue, current);
			if (comparinson == 0) {
				chosen.add(combination);
			} else if (comparinson > 0) {
				chosen.clear();
				chosen.add(combination);
				chosenValue = current;
			}
		}
		if (chosen.isEmpty()) {
			throw new NoCombinationToChooseException();
		} else if (chosen.size() == 1) {
			return chosen.iterator().next();
		} else {
			throw new TiebreakException(elements);
		}
	}

	public abstract Integer compare(Integer chosenValue, Integer current);

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
