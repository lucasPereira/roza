package br.ufsc.ine.leb.roza.clustering;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.exceptions.InsufficientRefereeException;
import br.ufsc.ine.leb.roza.exceptions.TiebreakException;

public class ComposedReferee implements Referee {

	private final List<Referee> referees;

	public ComposedReferee(Referee... referees) {
		if (referees.length < 2) {
			throw new InsufficientRefereeException();
		}
		this.referees = List.of(referees);
	}

	@Override
	public Combination untie(Set<Combination> elements) {
		Iterator<Referee> iterator = referees.iterator();
		while (iterator.hasNext()) {
			try {
				return iterator.next().untie(elements);
			} catch (TiebreakException ignored) {}
		}
		throw new TiebreakException(elements);
	}

	@Override
	public String toString() {
		return referees.get(0).toString();
	}
}
