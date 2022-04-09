package br.ufsc.ine.leb.roza.measurement;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import br.ufsc.ine.leb.roza.extraction.TestCase;
import br.ufsc.ine.leb.roza.materialization.MaterializationReport;
import br.ufsc.ine.leb.roza.materialization.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class LccssSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public SimilarityReport measureMoreTheOneTest(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		for (TestCaseMaterialization sourceMaterialization : materializations) {
			TestCase source = sourceMaterialization.getTestCase();
			List<RozaStatement> sourceFixtures = source.getFixtures();
			for (TestCaseMaterialization targetMaterialization : materializations) {
				TestCase target = targetMaterialization.getTestCase();
				if (!source.equals(target)) {
					List<RozaStatement> targetFixtures = target.getFixtures();
					Integer commonFixtures = lccss(sourceFixtures, targetFixtures);
					Integer reusedFixtures = commonFixtures * 2;
					Integer sourceOnlyFixtures = sourceFixtures.size() - commonFixtures;
					Integer targetOnlyFixtures = targetFixtures.size() - commonFixtures;
					BigDecimal totalFixtures = new BigDecimal(reusedFixtures + sourceOnlyFixtures + targetOnlyFixtures);
					BigDecimal score = source.equals(target) ? BigDecimal.ONE : (totalFixtures.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : new BigDecimal(reusedFixtures).divide(totalFixtures, MathContext.DECIMAL32));
					builder.add(source, target, score);
				}
			}
		}
		return builder.build();
	}

	private Integer lccss(List<RozaStatement> sourceFixtures, List<RozaStatement> targetFixtures) {
		Boolean contigous = true;
		Integer commonFixtures = 0;
		for (Integer index = 0; contigous && index < sourceFixtures.size() && index < targetFixtures.size(); index++) {
			RozaStatement sourceFixture = sourceFixtures.get(index);
			RozaStatement targetFixture = targetFixtures.get(index);
			contigous = sourceFixture.equals(targetFixture);
			if (contigous) {
				commonFixtures++;
			}
		}
		return commonFixtures;
	}

}
