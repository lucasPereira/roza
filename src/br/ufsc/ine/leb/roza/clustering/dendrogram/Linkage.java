package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;

interface Linkage {

	Link link(List<Cluster> clusters, SimilarityReport similarityReport);

}
