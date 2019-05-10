package br.ufsc.ine.leb.roza.measurer;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class LcsSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public SimilarityReport measure(MaterializationReport materializationReport) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		List<SimilarityAssessment> assessments = new LinkedList<>();
		for (TestCaseMaterialization sourceMaterialization : materializations) {
			TestCase source = sourceMaterialization.getTestCase();
			List<Statement> sourceFixtures = source.getFixtures();
			for (TestCaseMaterialization targetMaterialization : materializations) {
				TestCase target = targetMaterialization.getTestCase();
				List<Statement> targetFixtures = target.getFixtures();
				Boolean contigous = true;
				Integer commonFixtures = 0;
				for (Integer index = 0; contigous && index < sourceFixtures.size() && index < targetFixtures.size(); index++) {
					Statement sourceFixture = sourceFixtures.get(index);
					Statement targetFixture = targetFixtures.get(index);
					contigous = sourceFixture.equals(targetFixture);
					if (contigous) {
						commonFixtures++;
					}
				}
				Integer reusedFixtures = commonFixtures * 2;
				Integer sourceOnlyFixtures = sourceFixtures.size() - commonFixtures;
				Integer targetOnlyFixtures = targetFixtures.size() - commonFixtures;
				BigDecimal totalFixtures = new BigDecimal(reusedFixtures + sourceOnlyFixtures + targetOnlyFixtures);
				BigDecimal score = null;
				if (source.equals(target)) {
					score = BigDecimal.ONE;
				} else if (totalFixtures.equals(BigDecimal.ZERO)) {
					score = BigDecimal.ZERO;
				} else {
					score = new BigDecimal(reusedFixtures).divide(totalFixtures, MathContext.DECIMAL32);
				}
				SimilarityAssessment assessment = new SimilarityAssessment(source, target, score);
				assessments.add(assessment);
			}
		}
		return new SimilarityReport(assessments);
	}

}
