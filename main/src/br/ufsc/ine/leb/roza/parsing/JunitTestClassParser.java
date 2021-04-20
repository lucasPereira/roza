package br.ufsc.ine.leb.roza.parsing;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.TextFile;

public class JunitTestClassParser implements TestClassParser {

	private Class<? extends Annotation> testAnnotation;
	private Class<? extends Annotation> setupAnnotation;

	public JunitTestClassParser(Class<? extends Annotation> testAnnotation, Class<? extends Annotation> setupAnnotation) {
		this.testAnnotation = testAnnotation;
		this.setupAnnotation = setupAnnotation;
	}

	@Override
	public final List<TestClass> parse(List<TextFile> files) {
		List<TestClass> testClasses = new LinkedList<>();
		files.forEach((file) -> {
			CompilationUnit compilationUnit = JavaParser.parse(file.getContent());
			Optional<ClassOrInterfaceDeclaration> parsedTestClass = compilationUnit.findFirst(ClassOrInterfaceDeclaration.class);
			String name = parsedTestClass.get().getNameAsString();
			List<Field> fields = extractFields(parsedTestClass);
			List<SetupMethod> setupMethods = new LinkedList<>();
			List<TestMethod> testMethods = new LinkedList<>();
			List<MethodDeclaration> parsedMethods = parsedTestClass.get().findAll(MethodDeclaration.class);
			parsedMethods.forEach((parsedMethod) -> {
				extractSetupMethod(setupMethods, parsedMethod);
				extractTestMethod(testMethods, parsedMethod);
			});
			if (testMethods.size() > 0) {
				TestClass testClass = new TestClass(name, fields, setupMethods, testMethods);
				testClasses.add(testClass);
			}
		});
		return testClasses;
	}

	private List<Field> extractFields(Optional<ClassOrInterfaceDeclaration> parsedTestClass) {
		List<Field> fields = new LinkedList<>();
		parsedTestClass.get().findAll(FieldDeclaration.class).forEach((parsedField) -> {
			String type = parsedField.getElementType().asString();
			parsedField.getVariables().forEach((parsedVariable) -> {
				String filedName = parsedVariable.getName().asString();
				if (parsedVariable.getInitializer().isPresent()) {
					Expression initialization = parsedVariable.getInitializer().get();
					fields.add(new Field(type, filedName, new Statement(initialization.toString() + ";")));
				} else {
					fields.add(new Field(type, filedName));
				}
			});
		});
		return fields;
	}

	private void extractTestMethod(List<TestMethod> testMethods, MethodDeclaration parsedMethod) {
		Boolean hasTestAnnotation = parsedMethod.getAnnotationByClass(testAnnotation).isPresent();
		if (hasTestAnnotation) {
			List<Statement> statements = extractStatements(parsedMethod);
			TestMethod testMethod = new TestMethod(parsedMethod.getNameAsString(), statements);
			testMethods.add(testMethod);
		}
	}

	private void extractSetupMethod(List<SetupMethod> setupMethods, MethodDeclaration parsedMethod) {
		Boolean hasBeforeAnnotation = parsedMethod.getAnnotationByClass(setupAnnotation).isPresent();
		if (hasBeforeAnnotation) {
			List<Statement> statements = extractStatements(parsedMethod);
			SetupMethod setupMethod = new SetupMethod(parsedMethod.getNameAsString(), statements);
			setupMethods.add(setupMethod);
		}
	}

	private List<Statement> extractStatements(MethodDeclaration parsedMethod) {
		PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
		configuration.setEndOfLineCharacter(" ");
		configuration.setIndentSize(0);
		List<Statement> statements = new LinkedList<>();
		parsedMethod.getBody().get().getStatements().forEach(statement -> {
			if (statement.isExpressionStmt()) {
				ExpressionStmt expression = statement.asExpressionStmt();
				List<VariableDeclarationExpr> variableDeclarations = expression.findAll(VariableDeclarationExpr.class);
				if (variableDeclarations.isEmpty()) {
					statements.add(new Statement(statement.toString(configuration)));
				} else {
					variableDeclarations.forEach(variableDeclaration -> {
						Type type = variableDeclaration.getElementType();
						variableDeclaration.getVariables().forEach(variable -> {
							SimpleName name = variable.getName();
							Optional<Expression> initializer = variable.getInitializer();
							if (initializer.isPresent()) {
								VariableDeclarationExpr newStatement = new VariableDeclarationExpr(new VariableDeclarator(type, name, initializer.get()));
								statements.add(new Statement(newStatement.toString(configuration) + ";"));
							} else {
								VariableDeclarationExpr newStatement = new VariableDeclarationExpr(new VariableDeclarator(type, name));
								statements.add(new Statement(newStatement.toString(configuration) + ";"));
							}
						});
					});
				}
			} else {
				statements.add(new Statement(statement.toString(configuration)));
			}
		});
		return statements;
	}

}
