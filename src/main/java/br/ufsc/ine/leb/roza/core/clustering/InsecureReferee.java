package br.ufsc.ine.leb.roza.core.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.core.exceptions.NoCombinationToChooseException;
import br.ufsc.ine.leb.roza.core.exceptions.TiebreakException;

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
