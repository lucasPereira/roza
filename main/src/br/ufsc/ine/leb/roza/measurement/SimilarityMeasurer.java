package br.ufsc.ine.leb.roza.measurement;

import br.ufsc.ine.leb.roza.materialization.MaterializationReport;

public interface SimilarityMeasurer {

	SimilarityReport measure(MaterializationReport materializationReport);

}
