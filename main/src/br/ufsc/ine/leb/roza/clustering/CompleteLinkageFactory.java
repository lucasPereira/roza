package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.measurement.SimilarityReport;

public class CompleteLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new CompleteLinkage(report);
	}

}
