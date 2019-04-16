package br.ufsc.ine.leb.roza.measurer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

import br.ufsc.ine.leb.roza.MaterializationReport;
import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestField;
import br.ufsc.ine.leb.roza.TestMaterialization;

public class LongoUniformityMeasurer implements SimilarityMeasurer<TestField> {

	@Override
	public SimilarityReport<TestField> measure(MaterializationReport<TestField> materializationReport) {
		Map<TestField, Map<TestField, BigDecimal>> scores = new HashMap<>();
		List<TestMaterialization<TestField>> materializations = materializationReport.getMaterializations();
		List<SimilarityAssessment<TestField>> assessments = new LinkedList<>();
		for (TestMaterialization<TestField> source : materializations) {
			for (TestMaterialization<TestField> target : materializations) {
				TestField sourceTestCase = source.getTestBlock();
				TestField targetTestCase = target.getTestBlock();
				BigDecimal score = evaluateScore(scores, sourceTestCase, targetTestCase);
				SimilarityAssessment<TestField> assessment = new SimilarityAssessment<TestField>(sourceTestCase,
						targetTestCase, score);
				assessments.add(assessment);
			}
		}
		return new SimilarityReport<TestField>(assessments);
	}

	private BigDecimal evaluateScore(Map<TestField, Map<TestField, BigDecimal>> scores, TestField sourceTest,
			TestField targetTest) {
		Set<String> namesSource = getNames(sourceTest);
		Set<String> namesTarget = getNames(targetTest);
		Integer contains = 0;
		for (String name : namesSource) {
			if (namesTarget.contains(name)) {
				contains++;
			}
		}
		if (namesSource.size() < 1) {
			return BigDecimal.ONE;
		}
		BigDecimal score = new BigDecimal(contains).divide(new BigDecimal(namesSource.size()));
		return score;
	}

	private Set<String> getNames(TestField sourceTest) {
		Set<String> strings = new HashSet<String>();
		for (Statement statement : sourceTest.getFields()) {
			com.github.javaparser.ast.stmt.Statement aStatement = JavaParser.parseStatement(statement.getText());
			if (aStatement.isExpressionStmt()) {
				Expression expression = aStatement.asExpressionStmt().getExpression();
				if (expression.isVariableDeclarationExpr()) {
					VariableDeclarationExpr varExpression = (VariableDeclarationExpr) expression;
					for (VariableDeclarator var : varExpression.getVariables()) {
						strings.add(var.getNameAsString());
					}
				}
			}
		}
		return strings;
	}

}
