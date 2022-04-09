package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.measurement.SimilarityReport;

public interface LinkageFactory {

	Linkage create(SimilarityReport report);

}
