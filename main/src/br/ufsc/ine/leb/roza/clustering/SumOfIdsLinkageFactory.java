package br.ufsc.ine.leb.roza.clustering;

import br.ufsc.ine.leb.roza.SimilarityReport;

public class SumOfIdsLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new SumOfIdsLinkage();
	}

}
