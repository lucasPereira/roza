package br.ufsc.ine.leb.roza.parsing;

import java.lang.annotation.Annotation;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.Type;

import br.ufsc.ine.leb.roza.loading.TextFile;

public final class JavaTestClassParser implements TestClassParser {

	private Class<? extends Annotation> beforeAnnotation;
	private Class<? extends Annotation> testAnnotation;

	public JavaTestClassParser(Class<? extends Annotation> beforeAnnotation, Class<? extends Annotation> testAnnotation) {
		this.beforeAnnotation = beforeAnnotation;
		this.testAnnotation = testAnnotation;
	}

	@Override
	public List<TestClass> parse(List<TextFile> files) {
		TextFile file = files.get(0);
		List<TestClass> classes = new LinkedList<>();
		StaticJavaParser.getConfiguration().setAttributeComments(false);
		StaticJavaParser.getConfiguration().setLexicalPreservationEnabled(true);
		CompilationUnit compilationUnit = StaticJavaParser.parse(file.getContent());
		compilationUnit.findAll(ClassOrInterfaceDeclaration.class).forEach(classOrInterfaceDeclaration -> {
			List<Field> fields = parseFields(classOrInterfaceDeclaration);
			List<SetupMethod> setups = parseSetups(classOrInterfaceDeclaration);
			List<TestMethod> tests = parseTests(classOrInterfaceDeclaration);
			TestClass testClass = new TestClass(classOrInterfaceDeclaration, fields, setups, tests);
			classes.add(testClass);
		});
		return classes;
	}

	private List<Field> parseFields(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<Field> fields = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(FieldDeclaration.class).forEach(fieldDeclaration -> {
			Type type = fieldDeclaration.getCommonType();
			fieldDeclaration.getVariables().forEach(variableDeclarator -> {
				SimpleName variable = variableDeclarator.getName();
				Optional<Expression> initializer = variableDeclarator.getInitializer();
				VariableDeclarator refactoredVariableDeclarator = initializer.isPresent() ? new VariableDeclarator(type, variable, initializer.get()) : new VariableDeclarator(type, variable);
				NodeList<Modifier> modifiers = fieldDeclaration.getModifiers();
				NodeList<AnnotationExpr> annotations = fieldDeclaration.getAnnotations();
				NodeList<VariableDeclarator> variables = new NodeList<VariableDeclarator>(refactoredVariableDeclarator);
				FieldDeclaration refactoredFieldDeclaration = new FieldDeclaration(modifiers, annotations, variables);
				Field field = new Field(refactoredFieldDeclaration);
				fields.add(field);
			});
		});
		return fields;
	}

	private List<SetupMethod> parseSetups(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<SetupMethod> setups = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
			if (methodDeclaration.getAnnotationByClass(beforeAnnotation).isPresent()) {
				List<RozaStatement> statements = parseStatements(methodDeclaration);
				SetupMethod setup = new SetupMethod(methodDeclaration, statements);
				setups.add(setup);
			}
		});
		return setups;
	}

	private List<TestMethod> parseTests(ClassOrInterfaceDeclaration classOrInterfaceDeclaration) {
		List<TestMethod> tests = new LinkedList<>();
		classOrInterfaceDeclaration.findAll(MethodDeclaration.class).forEach(methodDeclaration -> {
			if (methodDeclaration.getAnnotationByClass(testAnnotation).isPresent()) {
				List<RozaStatement> statements = parseStatements(methodDeclaration);
				TestMethod test = new TestMethod(methodDeclaration, statements);
				tests.add(test);
			}
		});
		return tests;
	}

	private List<RozaStatement> parseStatements(MethodDeclaration declaration) {
		List<RozaStatement> statements = new LinkedList<>();
		declaration.getBody().get().getStatements().forEach(parsedStatement -> {
			if (parsedStatement.isExpressionStmt()) {
				ExpressionStmt expressionStatement = parsedStatement.asExpressionStmt();
				Expression expression = expressionStatement.getExpression();
				if (expression.isMethodCallExpr()) {
					MethodCallExpr methodCallExpression = expression.asMethodCallExpr();
					RozaStatement statement = new MethodCallStatement(methodCallExpression, parsedStatement);
					statements.add(statement);
				} else if (expression.isAssignExpr()) {
					AssignExpr assignExpression = expression.asAssignExpr();
					RozaStatement statement = new AssignStatement(assignExpression, parsedStatement);
					statements.add(statement);
				} else if (expression.isVariableDeclarationExpr()) {
					VariableDeclarationExpr variableDeclarationExpression = expression.asVariableDeclarationExpr();
					Type type = variableDeclarationExpression.getCommonType();
					variableDeclarationExpression.getVariables().forEach(variableDeclarator -> {
						SimpleName variable = variableDeclarator.getName();
						Optional<Expression> initializer = variableDeclarator.getInitializer();
						VariableDeclarator refactoredVariableDeclarator = initializer.isPresent() ? new VariableDeclarator(type, variable, initializer.get()) : new VariableDeclarator(type, variable);
						NodeList<Modifier> modifiers = variableDeclarationExpression.getModifiers();
						NodeList<AnnotationExpr> annotations = variableDeclarationExpression.getAnnotations();
						NodeList<VariableDeclarator> variables = new NodeList<VariableDeclarator>(refactoredVariableDeclarator);
						VariableDeclarationExpr refactoredVariableDeclarationExpression = new VariableDeclarationExpr(modifiers, annotations, variables);
						ExpressionStmt refactoredExpressionStatement = new ExpressionStmt(refactoredVariableDeclarationExpression);
						RozaStatement statement = new VariableDeclarationStatement(refactoredVariableDeclarationExpression, refactoredExpressionStatement);
						statements.add(statement);
					});
				} else {
					GenericStatement statement = new GenericStatement(parsedStatement);
					statements.add(statement);
				}
			} else {
				GenericStatement statement = new GenericStatement(parsedStatement);
				statements.add(statement);
			}
		});
		return statements;
	}

}
