package br.ufsc.ine.leb.roza.core.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.core.MaterializationReport;
import br.ufsc.ine.leb.roza.core.SimilarityReport;
import br.ufsc.ine.leb.roza.core.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;

public abstract class AbstractSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public final SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materialization = materializationReport.getMaterialization();
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		if (materialization.isEmpty()) {
			return builder.build();
		}
		if (materialization.size() == 1) {
			return builder.add(materialization.iterator().next().getTestCase()).build();
		}
		return measureMoreThanOne(materializationReport, builder);
	}

	abstract SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder);

}
