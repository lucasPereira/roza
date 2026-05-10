package br.ufsc.ine.leb.roza.measurement;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.SimilarityReportBuilder;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class LccssSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public SimilarityReport measureMoreThanOne(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materialization = materializationReport.getMaterialization();
		for (TestCaseMaterialization sourceMaterialization : materialization) {
			TestCase source = sourceMaterialization.getTestCase();
			List<Statement> sourceFixtures = source.getFixtures();
			for (TestCaseMaterialization targetMaterialization : materialization) {
				TestCase target = targetMaterialization.getTestCase();
				if (!source.equals(target)) {
					List<Statement> targetFixtures = target.getFixtures();
					Integer commonFixtures = lccss(sourceFixtures, targetFixtures);
					int reusedFixtures = commonFixtures * 2;
					int sourceOnlyFixtures = sourceFixtures.size() - commonFixtures;
					int targetOnlyFixtures = targetFixtures.size() - commonFixtures;
					BigDecimal totalFixtures = new BigDecimal(reusedFixtures + sourceOnlyFixtures + targetOnlyFixtures);
					BigDecimal score = source.equals(target) ? BigDecimal.ONE : (totalFixtures.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : new BigDecimal(reusedFixtures).divide(totalFixtures, MathContext.DECIMAL32));
					builder.add(source, target, score);
				}
			}
		}
		return builder.build();
	}

	private Integer lccss(List<Statement> sourceFixtures, List<Statement> targetFixtures) {
		boolean contigous = true;
		Integer commonFixtures = 0;
		for (int index = 0; contigous && index < sourceFixtures.size() && index < targetFixtures.size(); index++) {
			Statement sourceFixture = sourceFixtures.get(index);
			Statement targetFixture = targetFixtures.get(index);
			contigous = sourceFixture.equals(targetFixture);
			if (contigous) {
				commonFixtures++;
			}
		}
		return commonFixtures;
	}

}
