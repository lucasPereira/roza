package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.SimilarityReport;

public class SingleLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new SingleLinkage(report);
	}

}
