package br.ufsc.ine.leb.roza.core.clustering;

import java.util.Set;

import br.ufsc.ine.leb.roza.core.exceptions.NoCombinationToChooseException;

public class AnyClusterReferee implements Referee {

	@Override
	public Combination untie(Set<Combination> elements) {
		if (elements.isEmpty()) {
			throw new NoCombinationToChooseException();
		} else {
			return elements.iterator().next();
		}
	}

}
