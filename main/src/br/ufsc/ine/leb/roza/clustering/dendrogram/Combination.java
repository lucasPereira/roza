package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Arrays;

import br.ufsc.ine.leb.roza.Cluster;

class Combination {

	private Cluster first;
	private Cluster second;

	public Combination(Cluster first, Cluster second) {
		this.first = first;
		this.second = second;
	}

	public Cluster getFirst() {
		return first;
	}

	public Cluster getSecond() {
		return second;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Combination) {
			Combination other = (Combination) object;
			Boolean identic = first.equals(other.first) && second.equals(other.second);
			Boolean sameElements = first.equals(other.second) && second.equals(other.first);
			return identic || sameElements;
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
