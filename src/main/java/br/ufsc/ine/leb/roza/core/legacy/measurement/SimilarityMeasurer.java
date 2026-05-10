package br.ufsc.ine.leb.roza.core.legacy.measurement;

import br.ufsc.ine.leb.roza.core.legacy.MaterializationReport;
import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;

public interface SimilarityMeasurer {

	SimilarityReport measure(MaterializationReport materializationReport);

}
