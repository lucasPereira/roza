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

public class LcsSimilarityMeasurer extends AbstractSimilarityMeasurer implements SimilarityMeasurer {

	@Override
	public SimilarityReport measureMoreTheOneTest(MaterializationReport materializationReport, SimilarityReportBuilder builder) {
		List<TestCaseMaterialization> materializations = materializationReport.getMaterializations();
		for (TestCaseMaterialization sourceMaterialization : materializations) {
			TestCase source = sourceMaterialization.getTestCase();
			List<Statement> sourceFixtures = source.getFixtures();
			for (TestCaseMaterialization targetMaterialization : materializations) {
				TestCase target = targetMaterialization.getTestCase();
				if (!source.equals(target)) {
					List<Statement> targetFixtures = target.getFixtures();
					Integer commonFixtures = lcs(sourceFixtures, targetFixtures);
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

	private Integer lcs(List<Statement> sourceFixtures, List<Statement> targetFixtures) {
		Integer sizeSource = sourceFixtures.size();
		Integer sizeTarget = targetFixtures.size();
		Integer[][] matrix = new Integer[sizeSource + 1][sizeTarget + 1];
		Integer indexSource = null;
		Integer indexTarget = null;
		if (sizeSource == 0 || sizeTarget == 0) {
			return 0;
		}
		for (indexSource = 0; indexSource <= sizeSource; indexSource++) {
			for (indexTarget = 0; indexTarget <= sizeTarget; indexTarget++) {
				if (indexSource == 0 || indexTarget == 0) {
					matrix[indexSource][indexTarget] = 0;
				} else if (sourceFixtures.get(indexSource - 1).equals(targetFixtures.get(indexTarget - 1))) {
					Integer sourceAndTargetAncestor = matrix[indexSource - 1][indexTarget - 1];
					matrix[indexSource][indexTarget] = 1 + sourceAndTargetAncestor;
				} else {
					Integer sourceAncestor = matrix[indexSource - 1][indexTarget];
					Integer targetAncestor = matrix[indexSource][indexTarget - 1];
					matrix[indexSource][indexTarget] = Math.max(sourceAncestor, targetAncestor);
				}
			}
		}
		return matrix[sizeSource][sizeTarget];
	}

}
