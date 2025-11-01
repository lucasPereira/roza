package br.ufsc.ine.leb.roza.clustering;


import br.ufsc.ine.leb.roza.TestCase;

import java.util.List;

class Pair {

	private final TestCase first;
	private final TestCase second;

	public Pair(TestCase first, TestCase second) {
		this.first = first;
		this.second = second;
	}

	public TestCase getFirst() {
		return first;
	}

	public TestCase getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Pair) {
			Pair other = (Pair) object;
			return first.equals(other.first) && second.equals(other.second);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return first.hashCode() / 2 + second.hashCode() / 2;
	}

	@Override
	public String toString() {
		return List.of(first, second).toString();
	}

}
