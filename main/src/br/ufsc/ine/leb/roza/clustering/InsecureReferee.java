package br.ufsc.ine.leb.roza.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public class InsecureReferee implements Referee {

	@Override
	public Combination untie(Set<Combination> elements) {
		if (elements.isEmpty()) {
			throw new NoCombinationToChooseException();
		} else if (elements.size() == 1) {
			return elements.iterator().next();
		} else {
			throw new TiebreakException(elements);
		}
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
