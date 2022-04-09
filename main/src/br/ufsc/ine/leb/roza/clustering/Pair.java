package br.ufsc.ine.leb.roza.clustering;

import java.util.Arrays;

import br.ufsc.ine.leb.roza.extraction.TestCase;

class Pair {

	private TestCase first;
	private TestCase second;

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
		return Arrays.asList(first, second).toString();
	}

}
