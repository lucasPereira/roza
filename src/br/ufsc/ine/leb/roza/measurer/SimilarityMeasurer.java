package br.ufsc.ine.leb.roza.measurer;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestBlock;

public interface SimilarityMeasurer<T extends TestBlock> {

	SimilarityReport<T> measure(MaterializationReport<T> materializations);

}
