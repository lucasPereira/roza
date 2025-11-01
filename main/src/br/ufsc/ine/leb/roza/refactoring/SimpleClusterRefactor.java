package br.ufsc.ine.leb.roza.refactoring;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.utils.comparator.ClusterComparatorBySizeAndTestName;
import br.ufsc.ine.leb.roza.utils.comparator.TestCaseComparatorByName;

public class SimpleClusterRefactor implements ClusterRefactor {

	private final TestClassNamingStrategy namingStrategy;

	public SimpleClusterRefactor(TestClassNamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	@Override
	public List<TestClass> refactor(Set<Cluster> clusters) {
		List<TestClass> classes = new ArrayList<>(clusters.size());
		List<Cluster> clustersOrderedBySizeAndTestName = new ArrayList<>(clusters);
		clustersOrderedBySizeAndTestName.sort(new ClusterComparatorBySizeAndTestName());
		for (Cluster cluster : clustersOrderedBySizeAndTestName) {
			List<TestMethod> testMethods = new ArrayList<>(cluster.getTestCases().size());
			List<TestCase> testCases = new ArrayList<>(cluster.getTestCases());
			testCases.sort(new TestCaseComparatorByName());
			List<Statement> sharedFixtures = new StatementJoiner().join(testCases.stream().map(TestCase::getFixtures).collect(Collectors.toList()));
			for (TestCase testCase : testCases) {
				TestMethod testMethod = getTestMethod(testCase, sharedFixtures);
				testMethods.add(testMethod);
			}
			List<Field> fields = new LinkedList<>();
			List<SetupMethod> setupMethods = new LinkedList<>();
			if (!sharedFixtures.isEmpty()) {
				for (int index = 0; index < sharedFixtures.size(); index++) {
					Statement statement = sharedFixtures.get(index);
					Optional<VariableDeclarationExpr> declaration = JavaParser.parseStatement(statement.getText()).toExpressionStmt().orElseThrow().getExpression().toVariableDeclarationExpr();
					if (declaration.isPresent()) {
						VariableDeclarationExpr declarationExpression = declaration.get();
						sharedFixtures.set(index, new Statement(declarationExpression.getVariable(0).toString() + ";"));
						fields.add(new Field(declarationExpression.getElementType().asString(), declarationExpression.getVariable(0).getNameAsString()));
					}
				}
				SetupMethod setup = new SetupMethod("setup", sharedFixtures);
				setupMethods.add(setup);
			}
			String className = namingStrategy.nominate();
			TestClass testClass = new TestClass(className, fields, setupMethods, testMethods);
			classes.add(testClass);
		}
		return classes;
	}

	private static TestMethod getTestMethod(TestCase testCase, List<Statement> sharedFixtures) {
		List<Statement> fixtures = testCase.getFixtures();
		int firstFixtureIndex = sharedFixtures.size();
		int lastFixtureIndex = fixtures.isEmpty() ? 0 : fixtures.size();
		List<Statement> nonSharedFixtures = fixtures.subList(firstFixtureIndex, lastFixtureIndex);
		List<Statement> asserts = testCase.getAsserts();
		List<Statement> testMethodStatements = new ArrayList<>(nonSharedFixtures.size() + asserts.size());
		testMethodStatements.addAll(nonSharedFixtures);
		testMethodStatements.addAll(asserts);
		return new TestMethod(testCase.getName(), testMethodStatements);
	}

}
