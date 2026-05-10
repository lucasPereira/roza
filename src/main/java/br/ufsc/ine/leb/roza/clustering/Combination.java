package br.ufsc.ine.leb.roza.clustering;


import br.ufsc.ine.leb.roza.Cluster;

import java.util.List;

public class Combination {

	private final Cluster first;
	private final Cluster second;

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
		return List.of(first, second).toString();
	}

}
