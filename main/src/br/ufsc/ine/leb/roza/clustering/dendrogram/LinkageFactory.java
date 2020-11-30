package br.ufsc.ine.leb.roza.clustering.dendrogram;

import br.ufsc.ine.leb.roza.SimilarityReport;

public interface LinkageFactory {

	Linkage create(SimilarityReport report);

}
