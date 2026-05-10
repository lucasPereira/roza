package br.ufsc.ine.leb.roza.core.clustering;

import br.ufsc.ine.leb.roza.core.SimilarityReport;

public interface LinkageFactory {

	Linkage create(SimilarityReport report);

}
