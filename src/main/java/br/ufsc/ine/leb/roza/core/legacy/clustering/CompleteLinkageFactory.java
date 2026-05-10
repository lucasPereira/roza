package br.ufsc.ine.leb.roza.core.legacy.clustering;

import br.ufsc.ine.leb.roza.core.legacy.SimilarityReport;

public class CompleteLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new CompleteLinkage(report);
	}

}
