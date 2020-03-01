package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Set;

public class InsecureClusterReferee implements Referee {

	@Override
	public Set<Combination> untie(Set<Combination> elements) {
		return elements;
	}

}
