package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;

class Cluster {

	private List<TestCase> elements;

	public Cluster(TestCase test) {
		elements = new LinkedList<>();
		elements.add(test);
	}

	private Cluster(List<TestCase> merged) {
		elements = merged;
	}

	public List<TestCase> getElements() {
		return elements;
	}

	public Cluster merge(Cluster other) {
		List<TestCase> merged = new ArrayList<>(elements.size() + other.elements.size());
		merged.addAll(elements);
		merged.addAll(other.elements);
		return new Cluster(merged);
	}

	@Override
	public String toString() {
		return elements.toString();
	}

}
