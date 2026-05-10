package br.ufsc.ine.leb.roza.core.clustering;

import br.ufsc.ine.leb.roza.core.SimilarityReport;

public class CompleteLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new CompleteLinkage(report);
	}

}
