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
				Integer commonFixtures = lcs(sourceFixtures, targetFixtures, 0, 0);
				Integer reusedFixtures = commonFixtures * 2;
				Integer sourceOnlyFixtures = sourceFixtures.size() - commonFixtures;
				Integer targetOnlyFixtures = targetFixtures.size() - commonFixtures;
				BigDecimal totalFixtures = new BigDecimal(reusedFixtures + sourceOnlyFixtures + targetOnlyFixtures);
				BigDecimal score = source.equals(target) ? BigDecimal.ONE : (totalFixtures.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : new BigDecimal(reusedFixtures).divide(totalFixtures, MathContext.DECIMAL32));
				SimilarityAssessment assessment = new SimilarityAssessment(source, target, score);
				assessments.add(assessment);
			}
		}
		return new SimilarityReport(assessments);
	}

	private Integer lcs(List<Statement> sourceFixtures, List<Statement> targetFixtures, Integer sourceIndex, Integer targetIndex) {
		if (sourceIndex == sourceFixtures.size() || targetIndex == targetFixtures.size()) {
			return 0;
		}
		Statement sourceFixture = sourceFixtures.get(sourceIndex);
		Statement targetFixture = targetFixtures.get(targetIndex);
		if (sourceFixture.equals(targetFixture)) {
			Integer nextLcs = lcs(sourceFixtures, targetFixtures, sourceIndex + 1, targetIndex + 1);
			return 1 + nextLcs;
		}
		Integer nextSourceLcs = lcs(sourceFixtures, targetFixtures, sourceIndex + 1, targetIndex);
		Integer nextTargetLcs = lcs(sourceFixtures, targetFixtures, sourceIndex, targetIndex + 1);
		return Math.max(nextSourceLcs, nextTargetLcs);
	}

}
