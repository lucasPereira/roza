package br.ufsc.ine.leb.roza.exceptions;

import java.util.Set;

import br.ufsc.ine.leb.roza.clustering.Combination;

public class TiebreakException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final Set<Combination> tiebreak;

	public TiebreakException(Set<Combination> tiebreak) {
		this.tiebreak = tiebreak;
	}

	public Set<Combination> getTies() {
		return tiebreak;
	}

}
