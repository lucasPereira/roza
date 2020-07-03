package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Set;

public class InsecureReferee implements Referee {

	@Override
	public Set<Combination> untie(Set<Combination> elements) {
		return elements;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName();
	}

}
