package br.ufsc.ine.leb.roza.clustering.dendrogram;

class Link {

	private Cluster first;
	private Cluster second;

	public Link(Cluster first, Cluster second) {
		this.first = first;
		this.second = second;
	}

	public Cluster getFirst() {
		return first;
	}

	public Cluster getSecond() {
		return second;
	}

}
