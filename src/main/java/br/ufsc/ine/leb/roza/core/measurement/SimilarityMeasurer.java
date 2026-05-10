package br.ufsc.ine.leb.roza.core.measurement;

import br.ufsc.ine.leb.roza.core.MaterializationReport;
import br.ufsc.ine.leb.roza.core.SimilarityReport;

public interface SimilarityMeasurer {

	SimilarityReport measure(MaterializationReport materializationReport);

}
