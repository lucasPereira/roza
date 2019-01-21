package br.ufsc.ine.leb.roza.parser;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ExpressionStmt;

import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.TextFile;

public class Junit4TestClassParser implements TestClassParser {

	@Override
	public List<TestClass> parse(List<TextFile> files) {
		List<TestClass> testClasses = new LinkedList<>();
		files.forEach((file) -> {
			CompilationUnit compilationUnit = JavaParser.parse(file.getContent());
			Optional<ClassOrInterfaceDeclaration> parsedTestClass = compilationUnit.findFirst(ClassOrInterfaceDeclaration.class);
			String name = parsedTestClass.get().getNameAsString();
			List<SetupMethod> setupMethods = new LinkedList<>();
			List<TestMethod> testMethods = new LinkedList<>();
			List<MethodDeclaration> parsedMethods = parsedTestClass.get().findAll(MethodDeclaration.class);
			parsedMethods.forEach((parsedMethod) -> {
				extractSetupMethod(setupMethods, parsedMethod);
				extractTestMethod(testMethods, parsedMethod);
			});
			if (testMethods.size() > 0) {
				TestClass testClass = new TestClass(name, setupMethods, testMethods);
				testClasses.add(testClass);
			}
		});
		return testClasses;
	}

	private void extractTestMethod(List<TestMethod> testMethods, MethodDeclaration parsedMethod) {
		Boolean hasTestAnnotation = parsedMethod.getAnnotationByClass(Test.class).isPresent();
		if (hasTestAnnotation) {
			List<Statement> statements = extractStatements(parsedMethod);
			TestMethod testMethod = new TestMethod(parsedMethod.getNameAsString(), statements);
			testMethods.add(testMethod);
		}
	}

	private void extractSetupMethod(List<SetupMethod> setupMethods, MethodDeclaration parsedMethod) {
		Boolean hasBeforeAnnotation = parsedMethod.getAnnotationByClass(Before.class).isPresent();
		if (hasBeforeAnnotation) {
			List<Statement> statements = extractStatements(parsedMethod);
			SetupMethod setupMethod = new SetupMethod(parsedMethod.getNameAsString(), statements);
			setupMethods.add(setupMethod);
		}
	}

	private List<Statement> extractStatements(MethodDeclaration parsedMethod) {
		List<Statement> statements = new LinkedList<>();
		List<ExpressionStmt> parsedStatements = parsedMethod.findAll(ExpressionStmt.class);
		parsedStatements.forEach((parsedStatedment) -> {
			statements.add(new Statement(parsedStatedment.toString()));
		});
		return statements;
	}

}
