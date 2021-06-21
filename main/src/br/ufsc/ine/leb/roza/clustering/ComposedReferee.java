package br.ufsc.ine.leb.roza.clustering;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.exceptions.InsufficientRefereeException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public class ComposedReferee implements Referee {

	private List<Referee> referees;

	public ComposedReferee(Referee... referees) {
		if (referees.length < 2) {
			throw new InsufficientRefereeException();
		}
		this.referees = Arrays.asList(referees);
	}

	@Override
	public Combination untie(Set<Combination> elements) {
		Iterator<Referee> iterator = referees.iterator();
		while (iterator.hasNext()) {
			try {
				Combination combination = iterator.next().untie(elements);
				return combination;
			} catch (TiebreakException exception) {}
		}
		throw new TiebreakException(elements);
	}

}
