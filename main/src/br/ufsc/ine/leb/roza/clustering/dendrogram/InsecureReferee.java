package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Set;

class InsecureReferee implements Referee {

	@Override
	public Set<Combination> untie(Set<Combination> elements) {
		return elements;
	}

}
