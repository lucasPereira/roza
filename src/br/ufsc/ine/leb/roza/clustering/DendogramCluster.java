package br.ufsc.ine.leb.roza.clustering;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.TestCase;

public class DendogramCluster {

	private List<TestCase> elements;

	public DendogramCluster(TestCase test) {
		elements = new LinkedList<>();
		elements.add(test);
	}

	private DendogramCluster(List<TestCase> merged) {
		elements = merged;
	}

	public List<TestCase> getElements() {
		return elements;
	}

	public DendogramCluster merge(DendogramCluster other) {
		List<TestCase> merged = new ArrayList<>(elements.size() + other.elements.size());
		merged.addAll(elements);
		merged.addAll(other.elements);
		return new DendogramCluster(merged);
	}

	@Override
	public String toString() {
		return elements.toString();
	}
}
