package br.ufsc.ine.leb.roza.clustering.dendrogram;

import br.ufsc.ine.leb.roza.SimilarityReport;

public class AverageLinkageFactory implements LinkageFactory {

	@Override
	public Linkage create(SimilarityReport report) {
		return new AverageLinkage(report);
	}

}
