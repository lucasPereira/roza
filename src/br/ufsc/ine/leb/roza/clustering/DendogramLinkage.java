package br.ufsc.ine.leb.roza.clustering;

public class DendogramLinkage {

	private DendogramCluster first;
	private DendogramCluster second;

	public DendogramLinkage(DendogramCluster first, DendogramCluster second) {
		this.first = first;
		this.second = second;
	}

	public DendogramCluster getFirst() {
		return first;
	}

	public DendogramCluster getSecond() {
		return second;
	}

}
