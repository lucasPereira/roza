package br.ufsc.ine.leb.roza.measurement;

import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public abstract class AbstractSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public final SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		SimilarityReportBuilder builder = new SimilarityReportBuilder(false);
		if (materializations.size() == 0) {
			return builder.build();
		}
		if (materializations.size() == 1) {
			return builder.add(materializations.iterator().next().getTestCase()).build();
		}
		return measureMoreThanOne(materializationReport, builder);
	}

	abstract SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder);

}
