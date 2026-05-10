package br.ufsc.ine.leb.roza.core.clustering;

import br.ufsc.ine.leb.roza.core.SimilarityReport;

public class AverageLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new AverageLinkage(report);
	}

}
