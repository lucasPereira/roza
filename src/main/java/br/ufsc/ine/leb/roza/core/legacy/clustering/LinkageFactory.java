package br.ufsc.ine.leb.roza.core.legacy.clustering;

import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;

public interface LinkageFactory {

	Linkage create(SimilarityReport report);

}
