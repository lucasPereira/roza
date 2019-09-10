package br.ufsc.ine.leb.roza.clustering;

import java.util.List;

import br.ufsc.ine.leb.roza.SimilarityReport;

public interface DendogramLinkageMethod {

	DendogramLinkage link(List<DendogramCluster> clusters, SimilarityReport similarityReport);

}
