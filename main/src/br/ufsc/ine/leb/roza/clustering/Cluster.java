package br.ufsc.ine.leb.roza.clustering;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.ufsc.ine.leb.roza.extraction.TestCase;

public class Cluster {

	private Set<TestCase> elements;

	public Cluster(TestCase test) {
		elements = new HashSet<>();
		elements.add(test);
	}

	private Cluster(Set<TestCase> merged) {
		elements = merged;
	}

	public Cluster merge(Cluster other) {
		Set<TestCase> merged = new HashSet<>(elements.size() + other.elements.size());
		merged.addAll(elements);
		merged.addAll(other.elements);
		return new Cluster(merged);
	}

	public Set<TestCase> getTestCases() {
		return Collections.unmodifiableSet(elements);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Cluster) {
			Cluster other = (Cluster) object;
			return elements.equals(other.elements);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return elements.hashCode();
	}

	@Override
	public String toString() {
		return elements.toString();
	}

}
