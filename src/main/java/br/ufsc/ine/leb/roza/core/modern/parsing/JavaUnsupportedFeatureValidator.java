package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;

final class JavaUnsupportedFeatureValidator {

	private static final Set<String> SUPPORTED_METHOD_ANNOTATIONS = Set.of("Test", "Before", "BeforeEach");
	private static final Set<String> UNSUPPORTED_ANNOTATIONS = Set.of(
			"Nested",
			"After",
			"AfterEach",
			"BeforeClass",
			"AfterClass",
			"BeforeAll",
			"AfterAll",
			"Ignore",
			"Disabled",
			"RunWith",
			"Parameters",
			"Parameter",
			"ParameterizedTest",
			"CsvSource",
			"CsvFileSource",
			"ValueSource",
			"EnumSource",
			"MethodSource",
			"ArgumentsSource",
			"Theory",
			"DataPoint",
			"DataPoints",
			"TestFactory",
			"TestTemplate",
			"RepeatedTest",
			"FixMethodOrder",
			"TestMethodOrder",
			"Order",
			"TestInstance",
			"Category",
			"Tag",
			"Rule",
			"ClassRule",
			"ExtendWith",
			"RegisterExtension");

	List<UnsupportedFeatureDiagnostic> validate(CompilationUnit unit) {
		List<UnsupportedFeatureDiagnostic> diagnostics = new ArrayList<>();
		Set<String> helperMethodNames = helperMethodNames(unit);
		validateImports(unit, diagnostics);
		validateComments(unit, diagnostics);
		validateTopLevelClasses(unit, diagnostics);
		unit.findAll(ClassOrInterfaceDeclaration.class).forEach(type -> validateClass(type, diagnostics));
		unit.findAll(EnumDeclaration.class).forEach(type -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported enum declaration: " + type.getNameAsString())));
		unit.findAll(FieldDeclaration.class).forEach(field -> validateField(field, diagnostics, helperMethodNames));
		unit.findAll(MethodDeclaration.class).forEach(method -> validateMethod(method, diagnostics, helperMethodNames));
		validateFixtureCounts(unit, diagnostics);
		validateHelperOverloads(unit, diagnostics);
		return diagnostics;
	}

	private void validateImports(CompilationUnit unit, List<UnsupportedFeatureDiagnostic> diagnostics) {
		for (ImportDeclaration imported : unit.getImports()) {
			if (imported.isStatic()) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported static import: " + imported));
			}
			if (imported.isAsterisk()) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported wildcard import: " + imported));
			}
		}
	}

	private void validateComments(CompilationUnit unit, List<UnsupportedFeatureDiagnostic> diagnostics) {
		if (!unit.getAllContainedComments().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported comments in parsed test class"));
		}
	}

	private void validateTopLevelClasses(CompilationUnit unit, List<UnsupportedFeatureDiagnostic> diagnostics) {
		long topLevelClasses = unit.getTypes().stream().filter(type -> type.isClassOrInterfaceDeclaration()).count();
		if (topLevelClasses > 1) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported multiple top-level classes in the same file"));
		}
	}

	private void validateClass(ClassOrInterfaceDeclaration type, List<UnsupportedFeatureDiagnostic> diagnostics) {
		if (isNested(type)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported nested class: " + type.getNameAsString()));
		}
		if (!type.getExtendedTypes().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported test class inheritance: " + type.getNameAsString()));
		}
		if (type.isAbstract()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported abstract test class: " + type.getNameAsString()));
		}
		if (!type.getTypeParameters().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported generic test class: " + type.getNameAsString()));
		}
		type.getAnnotations().forEach(annotation -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported class annotation: " + annotation)));
		type.findAll(InitializerDeclaration.class).forEach(initializer -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported class initializer in: " + type.getNameAsString())));
		type.findAll(ConstructorDeclaration.class).forEach(constructor -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported explicit constructor: " + constructor.getNameAsString())));
	}

	private boolean isNested(ClassOrInterfaceDeclaration type) {
		return type.getParentNode().filter(parent -> parent instanceof ClassOrInterfaceDeclaration).isPresent();
	}

	private void validateField(FieldDeclaration field, List<UnsupportedFeatureDiagnostic> diagnostics, Set<String> helperMethodNames) {
		if (field.isStatic()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported static field: " + field));
		}
		if (field.isFinal()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported final field: " + field));
		}
		if (field.isVolatile()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported volatile field: " + field));
		}
		if (field.isTransient()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported transient field: " + field));
		}
		field.getAnnotations().forEach(annotation -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported field annotation: " + annotation)));
		field.getVariables().forEach(variable -> variable.getInitializer().ifPresent(initializer -> validateNodeBody(initializer, diagnostics, helperMethodNames)));
	}

	private void validateMethod(MethodDeclaration method, List<UnsupportedFeatureDiagnostic> diagnostics, Set<String> helperMethodNames) {
		validateAnnotations(method, diagnostics);
		if (isTestMethod(method)) {
			validateTestMethod(method, diagnostics);
		} else if (isFixtureMethod(method)) {
			validateFixtureMethod(method, diagnostics);
		} else {
			validateHelperMethod(method, diagnostics);
		}
		method.getBody().ifPresent(body -> validateNodeBody(body, diagnostics, helperMethodNames));
	}

	private void validateAnnotations(MethodDeclaration method, List<UnsupportedFeatureDiagnostic> diagnostics) {
		Set<String> seen = new HashSet<>();
		for (AnnotationExpr annotation : method.getAnnotations()) {
			String name = simpleName(annotation);
			if (!seen.add(name)) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported repeated annotation: " + annotation));
			}
			if (UNSUPPORTED_ANNOTATIONS.contains(name)) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported annotation: " + annotation));
			}
			if (!SUPPORTED_METHOD_ANNOTATIONS.contains(name)) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported method annotation: " + annotation));
			}
			if ("Test".equals(name) && !annotation.isMarkerAnnotationExpr()) {
				diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported @Test attributes: " + annotation));
			}
		}
	}

	private void validateTestMethod(MethodDeclaration method, List<UnsupportedFeatureDiagnostic> diagnostics) {
		if (!method.getParameters().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported test method with parameters: " + method.getNameAsString()));
		}
		if (!method.getType().isVoidType()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported test method return type: " + method.getNameAsString()));
		}
		if (method.isPrivate()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported private test method: " + method.getNameAsString()));
		}
		if (method.isStatic()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported static test method: " + method.getNameAsString()));
		}
		if (method.getBody().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported test method without body: " + method.getNameAsString()));
		}
	}

	private void validateFixtureMethod(MethodDeclaration method, List<UnsupportedFeatureDiagnostic> diagnostics) {
		if (method.isStatic()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported static fixture method: " + method.getNameAsString()));
		}
		if (!method.getParameters().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported fixture method with parameters: " + method.getNameAsString()));
		}
		if (!method.getType().isVoidType()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported fixture method return type: " + method.getNameAsString()));
		}
	}

	private void validateHelperMethod(MethodDeclaration method, List<UnsupportedFeatureDiagnostic> diagnostics) {
		if (method.isStatic()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported static helper method: " + method.getNameAsString()));
		}
		if (!method.getTypeParameters().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported generic helper method: " + method.getNameAsString()));
		}
		if (method.getParameters().stream().anyMatch(parameter -> parameter.isVarArgs())) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported helper method with varargs: " + method.getNameAsString()));
		}
		if (method.isSynchronized()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported synchronized helper method: " + method.getNameAsString()));
		}
		if (method.isNative()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported native helper method: " + method.getNameAsString()));
		}
		if (method.getBody().isEmpty()) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported helper method without body: " + method.getNameAsString()));
		}
		method.getBody().ifPresent(body -> body.findAll(ReturnStmt.class).stream()
				.filter(statement -> statement.getExpression().isPresent())
				.forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported helper method return value: " + method.getNameAsString()))));
	}

	private void validateHelperOverloads(CompilationUnit unit, List<UnsupportedFeatureDiagnostic> diagnostics) {
		Map<String, Integer> helperNames = new HashMap<>();
		unit.findAll(MethodDeclaration.class).stream()
				.filter(method -> !isTestMethod(method))
				.filter(method -> !isFixtureMethod(method))
				.forEach(method -> helperNames.merge(method.getNameAsString(), 1, Integer::sum));
		helperNames.entrySet().stream()
				.filter(entry -> entry.getValue() > 1)
				.forEach(entry -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported overloaded helper method: " + entry.getKey())));
	}

	private void validateFixtureCounts(CompilationUnit unit, List<UnsupportedFeatureDiagnostic> diagnostics) {
		long beforeFixtures = countMethodsAnnotatedWith(unit, "Before") + countMethodsAnnotatedWith(unit, "BeforeEach");
		long afterFixtures = countMethodsAnnotatedWith(unit, "After") + countMethodsAnnotatedWith(unit, "AfterEach");
		if (beforeFixtures > 1) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported multiple @Before fixtures"));
		}
		if (afterFixtures > 1) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported multiple @After fixtures"));
		}
	}

	private long countMethodsAnnotatedWith(CompilationUnit unit, String annotationName) {
		return unit.findAll(MethodDeclaration.class).stream()
				.filter(method -> hasAnnotation(method, annotationName))
				.count();
	}

	private Set<String> helperMethodNames(CompilationUnit unit) {
		Set<String> helperNames = new HashSet<>();
		unit.findAll(MethodDeclaration.class).stream()
				.filter(method -> !isTestMethod(method))
				.filter(method -> !isFixtureMethod(method))
				.forEach(method -> helperNames.add(method.getNameAsString()));
		return helperNames;
	}

	private void validateNodeBody(Node node, List<UnsupportedFeatureDiagnostic> diagnostics, Set<String> helperMethodNames) {
		node.findAll(LambdaExpr.class).forEach(lambda -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported lambda expression")));
		node.findAll(MethodReferenceExpr.class).forEach(reference -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported method reference")));
		node.findAll(ObjectCreationExpr.class).stream()
				.filter(creation -> creation.getAnonymousClassBody().isPresent())
				.forEach(creation -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported anonymous class")));
		node.findAll(ObjectCreationExpr.class).stream()
				.filter(creation -> "Thread".equals(creation.getType().asString()))
				.forEach(creation -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported thread creation")));
		node.findAll(ObjectCreationExpr.class).stream()
				.filter(creation -> Set.of("Socket", "URL").contains(creation.getType().asString()))
				.forEach(creation -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported network object creation: " + creation.getType())));
		node.findAll(ClassOrInterfaceDeclaration.class).forEach(type -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported local class: " + type.getNameAsString())));
		node.findAll(TryStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported try statement")));
		node.findAll(SwitchStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported switch statement")));
		node.findAll(SynchronizedStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported synchronized block")));
		node.findAll(LabeledStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported labeled statement")));
		node.findAll(BreakStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported break statement")));
		node.findAll(ContinueStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported continue statement")));
		node.findAll(ThrowStmt.class).forEach(statement -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported explicit throw statement")));
		node.findAll(ThisExpr.class).forEach(expression -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported explicit this expression")));
		node.findAll(SuperExpr.class).forEach(expression -> diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported explicit super expression")));
		node.findAll(MethodCallExpr.class).forEach(call -> validateMethodCall(call, diagnostics, helperMethodNames));
	}

	private void validateMethodCall(MethodCallExpr call, List<UnsupportedFeatureDiagnostic> diagnostics, Set<String> helperMethodNames) {
		String scope = call.getScope().map(Object::toString).orElse("");
		String name = call.getNameAsString();
		if ("Class".equals(scope) && "forName".equals(name)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported reflection call: " + call));
		}
		if (Set.of("getDeclaredMethod", "getDeclaredField", "getMethod", "getField").contains(name)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported reflection call: " + call));
		}
		if (Set.of("Files", "Paths", "DriverManager", "URL", "Socket", "Clock", "Instant", "LocalDate", "LocalDateTime").contains(scope)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported side-effect or time-related call: " + call));
		}
		if ("System".equals(scope) && Set.of("currentTimeMillis", "nanoTime", "exit", "getProperty").contains(name)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported system call: " + call));
		}
		if (Set.of("CompletableFuture", "Executors").contains(scope)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported async call: " + call));
		}
		if (scope.isEmpty() && !helperMethodNames.contains(name) && !isAllowedUnscopedCall(name)) {
			diagnostics.add(new UnsupportedFeatureDiagnostic("Unsupported call to undeclared helper: " + name));
		}
	}

	private boolean isAllowedUnscopedCall(String name) {
		return JunitAssertionMethods.contains(name);
	}

	private boolean hasAnnotation(MethodDeclaration method, String annotationName) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(annotationName::equals);
	}

	private boolean isTestMethod(MethodDeclaration method) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch("Test"::equals);
	}

	private boolean isFixtureMethod(MethodDeclaration method) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(name -> Set.of("Before", "BeforeEach").contains(name));
	}

	private String simpleName(AnnotationExpr annotation) {
		String name = annotation.getNameAsString();
		int separator = name.lastIndexOf('.');
		if (separator == -1) {
			return name;
		}
		return name.substring(separator + 1);
	}
}
