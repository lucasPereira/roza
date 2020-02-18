package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;

public class FirstLinkage implements Linkage {

	@Override
	public Link link(List<Cluster> clusters, SimilarityReport similarityReport) {
		return new Link(clusters.get(0), clusters.get(1));
	}

}
