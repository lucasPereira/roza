package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;

public interface SimilarityMeasurer {

	SimilarityReport measure(MaterializationReport materializationReport);

}
