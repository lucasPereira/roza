package br.ufsc.ine.leb.roza.clustering.dendrogram;

import br.ufsc.ine.leb.roza.SimilarityReport;

interface Linkage {

	Link link(ClustersToMerge clusters, SimilarityReport similarityReport);

}
