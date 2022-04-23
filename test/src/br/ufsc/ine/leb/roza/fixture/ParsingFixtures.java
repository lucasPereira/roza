package br.ufsc.ine.leb.roza.fixture;

import java.util.Arrays;
import java.util.List;

import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.AssignExpr.Operator;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.VoidType;

import br.ufsc.ine.leb.roza.parsing.AssignStatement;
import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.GenericStatement;
import br.ufsc.ine.leb.roza.parsing.MethodCallStatement;
import br.ufsc.ine.leb.roza.parsing.RozaStatement;
import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.TestClass;
import br.ufsc.ine.leb.roza.parsing.TestMethod;
import br.ufsc.ine.leb.roza.parsing.VariableDeclarationStatement;

public class ParsingFixtures {

	public static TestClass testClass(String name) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Boolean isInterface = false;
		SimpleName simpleName = new SimpleName(name);
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		NodeList<ClassOrInterfaceType> extendedTypes = new NodeList<>();
		NodeList<ClassOrInterfaceType> implementedTypes = new NodeList<>();
		NodeList<BodyDeclaration<?>> members = new NodeList<>();
		ClassOrInterfaceDeclaration declaration = new ClassOrInterfaceDeclaration(modifiers, annotations, isInterface, simpleName, typeParameters, extendedTypes, implementedTypes, members);
		return new TestClass(declaration, Arrays.asList(), Arrays.asList(), Arrays.asList());
	}

	public static TestClass testClass(String name, List<Field> fields, List<SetupMethod> setups, List<TestMethod> tests) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Boolean isInterface = false;
		SimpleName simpleName = new SimpleName(name);
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		NodeList<ClassOrInterfaceType> extendedTypes = new NodeList<>();
		NodeList<ClassOrInterfaceType> implementedTypes = new NodeList<>();
		NodeList<BodyDeclaration<?>> members = new NodeList<>();
		ClassOrInterfaceDeclaration declaration = new ClassOrInterfaceDeclaration(modifiers, annotations, isInterface, simpleName, typeParameters, extendedTypes, implementedTypes, members);
		return new TestClass(declaration, fields, setups, tests);
	}

	public static SetupMethod setupMethod(String name) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		Type type = new VoidType();
		SimpleName simpleName = new SimpleName(name);
		NodeList<Parameter> parameters = new NodeList<>();
		NodeList<ReferenceType> throwns = new NodeList<>();
		BlockStmt body = new BlockStmt();
		ReceiverParameter receivedParameter = null;
		MethodDeclaration declaration = new MethodDeclaration(modifiers, annotations, typeParameters, type, simpleName, parameters, throwns, body, receivedParameter);
		return new SetupMethod(declaration, Arrays.asList());
	}

	public static SetupMethod setupMethod(String name, List<RozaStatement> statements) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		Type type = new VoidType();
		SimpleName simpleName = new SimpleName(name);
		NodeList<Parameter> parameters = new NodeList<>();
		NodeList<ReferenceType> throwns = new NodeList<>();
		BlockStmt body = new BlockStmt();
		ReceiverParameter receivedParameter = null;
		MethodDeclaration declaration = new MethodDeclaration(modifiers, annotations, typeParameters, type, simpleName, parameters, throwns, body, receivedParameter);
		return new SetupMethod(declaration, statements);
	}

	public static TestMethod testMethod(String name) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		Type type = new VoidType();
		SimpleName simpleName = new SimpleName(name);
		NodeList<Parameter> parameters = new NodeList<>();
		NodeList<ReferenceType> throwns = new NodeList<>();
		BlockStmt body = new BlockStmt();
		ReceiverParameter receivedParameter = null;
		MethodDeclaration declaration = new MethodDeclaration(modifiers, annotations, typeParameters, type, simpleName, parameters, throwns, body, receivedParameter);
		return new TestMethod(declaration, Arrays.asList());
	}

	public static TestMethod testMethod(String name, List<RozaStatement> statements) {
		Modifier modifier = Modifier.publicModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		NodeList<TypeParameter> typeParameters = new NodeList<>();
		Type type = new VoidType();
		SimpleName simpleName = new SimpleName(name);
		NodeList<Parameter> parameters = new NodeList<>();
		NodeList<ReferenceType> throwns = new NodeList<>();
		BlockStmt body = new BlockStmt();
		ReceiverParameter receivedParameter = null;
		MethodDeclaration declaration = new MethodDeclaration(modifiers, annotations, typeParameters, type, simpleName, parameters, throwns, body, receivedParameter);
		return new TestMethod(declaration, statements);
	}

	public static Field fieldInteger(String name, Integer value) {
		Modifier modifier = Modifier.privateModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Type type = PrimitiveType.intType().getType().toBoxedType();
		SimpleName simpleName = new SimpleName(name);
		Expression initalization = new IntegerLiteralExpr(value.toString());
		VariableDeclarator variable = new VariableDeclarator(type, simpleName, initalization);
		NodeList<VariableDeclarator> variables = new NodeList<>(variable);
		FieldDeclaration declaration = new FieldDeclaration(modifiers, annotations, variables);
		return new Field(declaration);
	}

	public static Field fieldInteger(String name) {
		Modifier modifier = Modifier.privateModifier();
		NodeList<Modifier> modifiers = new NodeList<>(modifier);
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Type type = PrimitiveType.intType().getType().toBoxedType();
		SimpleName simpleName = new SimpleName(name);
		VariableDeclarator variable = new VariableDeclarator(type, simpleName);
		NodeList<VariableDeclarator> variables = new NodeList<>(variable);
		FieldDeclaration declaration = new FieldDeclaration(modifiers, annotations, variables);
		return new Field(declaration);
	}

	public static GenericStatement whileTrueBreak() {
		BooleanLiteralExpr condition = new BooleanLiteralExpr(true);
		Statement body = new BreakStmt();
		Statement statement = new WhileStmt(condition, body);
		return new GenericStatement(statement);
	}

	public static GenericStatement whileFalseEmpty() {
		Expression condition = new BooleanLiteralExpr(false);
		Statement body = new BlockStmt();
		Statement statement = new WhileStmt(condition, body);
		return new GenericStatement(statement);
	}

	public static AssignStatement assignInteger(String name, Integer value) {
		SimpleName simpleName = new SimpleName(name);
		Expression target = new NameExpr(simpleName);
		Expression initalization = new IntegerLiteralExpr(value.toString());
		AssignExpr expression = new AssignExpr(target, initalization, Operator.ASSIGN);
		Statement statement = new ExpressionStmt(expression);
		return new AssignStatement(expression, statement);
	}

	public static MethodCallStatement methodCall(String name) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);
		NodeList<Expression> arguments = new NodeList<>();
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithNullAndMethodCallWithScopeParameters(String methodName, Class<?> parameterScope, String parameterMethodName) {
		ClassOrInterfaceType parentScope = null;
		NodeList<Type> parameterScopeTypeArguments = new NodeList<>();
		NodeList<AnnotationExpr> parameterScopeAnnotations = new NodeList<>();
		SimpleName parameterScopeSimpleName = new SimpleName(parameterScope.getSimpleName());
		Type parameterScopeType = new ClassOrInterfaceType(parentScope, parameterScopeSimpleName, parameterScopeTypeArguments, parameterScopeAnnotations);
		Expression parameterScopeExpression = new TypeExpr(parameterScopeType);

		NodeList<Type> parameterTypeArguments = new NodeList<>();
		SimpleName parameterSimpleName = new SimpleName(parameterMethodName);
		NodeList<Expression> parameterArguments = new NodeList<>();
		MethodCallExpr parameterExpression = new MethodCallExpr(parameterScopeExpression, parameterTypeArguments, parameterSimpleName, parameterArguments);

		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(methodName);
		NullLiteralExpr firstArgument = new NullLiteralExpr();
		NodeList<Expression> arguments = new NodeList<>(firstArgument, parameterExpression);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(parameterExpression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithTwoIntegerArraysWithOneElementEachParameters(String name, Integer first, Integer second) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);

		IntegerLiteralExpr firstArrayArgument = new IntegerLiteralExpr(first.toString());
		IntegerLiteralExpr firstArrayDimension = new IntegerLiteralExpr(Integer.valueOf(1).toString());
		NodeList<AnnotationExpr> firstArrayAnnotations = new NodeList<>();
		ArrayCreationLevel firstArrayLevel = new ArrayCreationLevel(firstArrayDimension, firstArrayAnnotations);
		NodeList<ArrayCreationLevel> firstArrayLevels = new NodeList<ArrayCreationLevel>(firstArrayLevel);
		NodeList<Expression> firstArratValues = new NodeList<>(firstArrayArgument);
		ArrayInitializerExpr firstArrayInitializer = new ArrayInitializerExpr(firstArratValues);
		Type firstArrayType = PrimitiveType.intType().toBoxedType();
		ArrayCreationExpr firstArray = new ArrayCreationExpr(firstArrayType, firstArrayLevels, firstArrayInitializer);

		IntegerLiteralExpr secondArrayArgument = new IntegerLiteralExpr(second.toString());
		IntegerLiteralExpr secondArrayDimension = new IntegerLiteralExpr(Integer.valueOf(1).toString());
		NodeList<AnnotationExpr> secondArrayAnnotations = new NodeList<>();
		ArrayCreationLevel secondArrayLevel = new ArrayCreationLevel(secondArrayDimension, secondArrayAnnotations);
		NodeList<ArrayCreationLevel> secondArrayLevels = new NodeList<ArrayCreationLevel>(secondArrayLevel);
		NodeList<Expression> secondArrayValues = new NodeList<>(secondArrayArgument);
		ArrayInitializerExpr secondArrayInitializer = new ArrayInitializerExpr(secondArrayValues);
		Type secondArrayType = PrimitiveType.intType().toBoxedType();
		ArrayCreationExpr secondArray = new ArrayCreationExpr(secondArrayType, secondArrayLevels, secondArrayInitializer);

		NodeList<Expression> arguments = new NodeList<>(firstArray, secondArray);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithOneIntegerParameter(String name, Integer parameter) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);
		IntegerLiteralExpr argument = new IntegerLiteralExpr(parameter.toString());
		NodeList<Expression> arguments = new NodeList<>(argument);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithTwoIntegerParameters(String name, Integer first, Integer second) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);
		IntegerLiteralExpr firstArgument = new IntegerLiteralExpr(first.toString());
		IntegerLiteralExpr secondArgument = new IntegerLiteralExpr(second.toString());
		NodeList<Expression> arguments = new NodeList<>(firstArgument, secondArgument);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithOneBooleanParameter(String name, Boolean parameter) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);
		BooleanLiteralExpr argument = new BooleanLiteralExpr(parameter);
		NodeList<Expression> arguments = new NodeList<>(argument);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static MethodCallStatement methodCallWithOneNullParameter(String name) {
		Expression scope = null;
		NodeList<Type> typeArguments = new NodeList<>();
		SimpleName simpleName = new SimpleName(name);
		NullLiteralExpr argument = new NullLiteralExpr();
		NodeList<Expression> arguments = new NodeList<>(argument);
		MethodCallExpr expression = new MethodCallExpr(scope, typeArguments, simpleName, arguments);
		Statement statement = new ExpressionStmt(expression);
		return new MethodCallStatement(expression, statement);
	}

	public static VariableDeclarationStatement variableDeclarationInteger(String name) {
		NodeList<Modifier> modifiers = new NodeList<>();
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Type type = PrimitiveType.intType().getType().toBoxedType();
		SimpleName simpleName = new SimpleName(name);
		VariableDeclarator variable = new VariableDeclarator(type, simpleName);
		NodeList<VariableDeclarator> variables = new NodeList<>(variable);
		VariableDeclarationExpr expression = new VariableDeclarationExpr(modifiers, annotations, variables);
		Statement statement = new ExpressionStmt(expression);
		return new VariableDeclarationStatement(expression, statement);
	}

	public static VariableDeclarationStatement variableDeclarationInteger(String name, Integer value) {
		NodeList<Modifier> modifiers = new NodeList<>();
		NodeList<AnnotationExpr> annotations = new NodeList<>();
		Type type = PrimitiveType.intType().getType().toBoxedType();
		SimpleName simpleName = new SimpleName(name);
		Expression initalization = new IntegerLiteralExpr(value.toString());
		VariableDeclarator variable = new VariableDeclarator(type, simpleName, initalization);
		NodeList<VariableDeclarator> variables = new NodeList<>(variable);
		VariableDeclarationExpr expression = new VariableDeclarationExpr(modifiers, annotations, variables);
		Statement statement = new ExpressionStmt(expression);
		return new VariableDeclarationStatement(expression, statement);
	}

}
