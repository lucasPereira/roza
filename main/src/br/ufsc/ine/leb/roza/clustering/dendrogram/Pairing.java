package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;

class Pairing {

	private Cluster first;
	private Cluster second;

	public Pairing(Cluster first, Cluster second) {
		this.first = first;
		this.second = second;
	}

	public Set<Pair> getPairs() {
		Set<Pair> pairs = new HashSet<>();
		for (TestCase firstTestCase : first.getTestCases()) {
			for (TestCase secondTestCase : second.getTestCases()) {
				pairs.add(new Pair(firstTestCase, secondTestCase));
				pairs.add(new Pair(secondTestCase, firstTestCase));
			}
		}
		return pairs;
	}

}
