package br.ufsc.ine.leb.roza.refactoring;

import java.util.ArrayList;
import java.util.Collections;
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

	private TestClassNamingStrategy namingStrategy;

	public SimpleClusterRefactor(TestClassNamingStrategy namingStrategy) {
		this.namingStrategy = namingStrategy;
	}

	@Override
	public List<TestClass> refactor(Set<Cluster> clusters) {
		List<TestClass> classes = new ArrayList<TestClass>(clusters.size());
		List<Cluster> clustersOrderedBySizeAndTestName = new ArrayList<>(clusters);
		Collections.sort(clustersOrderedBySizeAndTestName, new ClusterComparatorBySizeAndTestName());
		for (Cluster cluster : clustersOrderedBySizeAndTestName) {
			List<TestMethod> testMethods = new ArrayList<>(cluster.getTestCases().size());
			List<TestCase> testCases = new ArrayList<>(cluster.getTestCases());
			Collections.sort(testCases, new TestCaseComparatorByName());
			List<Statement> sharedFixtures = new StatementJoiner().join(testCases.stream().map(testCase -> testCase.getFixtures()).collect(Collectors.toList()));
			for (TestCase testCase : testCases) {
				List<Statement> fixtures = testCase.getFixtures();
				Integer firstFixtureIndex = sharedFixtures.size();
				Integer lastFixtureIndex = fixtures.isEmpty() ? 0 : fixtures.size();
				List<Statement> nonSharedFixtures = fixtures.subList(firstFixtureIndex, lastFixtureIndex);
				List<Statement> asserts = testCase.getAsserts();
				List<Statement> testMethodStatements = new ArrayList<Statement>(nonSharedFixtures.size() + asserts.size());
				testMethodStatements.addAll(nonSharedFixtures);
				testMethodStatements.addAll(asserts);
				TestMethod testMethod = new TestMethod(testCase.getName(), testMethodStatements);
				testMethods.add(testMethod);
			}
			List<Field> fields = new LinkedList<>();
			List<SetupMethod> setupMethods = new LinkedList<>();
			if (!sharedFixtures.isEmpty()) {
				for (Integer index = 0; index < sharedFixtures.size(); index++) {
					Statement statement = sharedFixtures.get(index);
					Optional<VariableDeclarationExpr> declaration = JavaParser.parseStatement(statement.getText()).toExpressionStmt().get().getExpression().toVariableDeclarationExpr();
					if (declaration.isPresent()) {
						VariableDeclarationExpr declarationExpression = declaration.get();
						sharedFixtures.set(index, new Statement(declarationExpression.getVariable(0).toString() + ";"));
						fields.add(new Field(declarationExpression.getElementType().asString(), declarationExpression.getVariable(0).getNameAsString()));
					}
				}
				SetupMethod setup = new SetupMethod("setup", sharedFixtures);
				setupMethods.add(setup);
			}
			String className = namingStrategy.nominate(fields, setupMethods, testMethods);
			TestClass testClass = new TestClass(className, fields, setupMethods, testMethods);
			classes.add(testClass);
		}
		return classes;
	}

}
