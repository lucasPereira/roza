package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.Iterator;

import br.ufsc.ine.leb.roza.SimilarityReport;

public class FirstLinkage implements Linkage {

	@Override
	public Link link(ClustersToMerge clusters, SimilarityReport similarityReport) {
		Iterator<Cluster> iterator = clusters.iterator();
		return new Link(iterator.next(), iterator.next());
	}

}
