package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Iterator;
import java.util.List;

import br.ufsc.ine.leb.roza.exceptions.NoClustersToMergeException;

public class ClustersToMerge implements Iterable<Cluster> {

	private List<Cluster> clusters;

	public ClustersToMerge(List<Cluster> clusters) {
		if (clusters.size() < 2) {
			throw new NoClustersToMergeException();
		}
		this.clusters = clusters;
	}

	@Override
	public Iterator<Cluster> iterator() {
		return clusters.iterator();
	}

}
