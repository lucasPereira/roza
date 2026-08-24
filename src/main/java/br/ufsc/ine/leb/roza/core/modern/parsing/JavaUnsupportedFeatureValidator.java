package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalRecordDeclarationStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;

final class JavaUnsupportedFeatureValidator {

	private static final Set<String> SUPPORTED_METHOD_ANNOTATIONS = Set.of("Test", "Before", "BeforeEach");
	private static final Set<String> TEST_METHOD_ONLY_UNSUPPORTED_ANNOTATIONS = Set.of("ParameterizedTest", "Theory", "TestFactory", "TestTemplate", "RepeatedTest");
	private static final Set<String> LIFECYCLE_ANNOTATIONS = Set.of("BeforeClass", "After", "AfterEach", "AfterClass", "BeforeAll", "AfterAll");

	List<TestCodeViolation> validate(CompilationUnit unit) {
		return validate(unit, false);
	}

	List<TestCodeViolation> validateHelperClass(CompilationUnit unit) {
		return validate(unit, true);
	}

	private List<TestCodeViolation> validate(CompilationUnit unit, boolean helperClass) {
		if (helperClass) {
			return List.of();
		}
		String testClassName = testClassName(unit);
		List<TestCodeViolation> violations = new ArrayList<>();
		validateImports(unit, testClassName, violations);
		validateTopLevelClasses(unit, testClassName, violations);
		unit.findAll(ClassOrInterfaceDeclaration.class).forEach(type -> validateClass(type, testClassName, violations));
		unit.findAll(EnumDeclaration.class).forEach(type -> classViolation(violations, testClassName, "Enum declaration: " + type.getNameAsString(), type));
		unit.findAll(RecordDeclaration.class).forEach(type -> {
			if (isNestedRecord(type)) {
				classViolation(violations, testClassName, "Nested record: " + type.getNameAsString(), type);
			}
		});
		if (!helperClass) {
			unit.findAll(FieldDeclaration.class).forEach(field -> validateField(field, testClassName, violations));
		}
		unit.findAll(MethodDeclaration.class).forEach(method -> validateMethod(method, testClassName, violations, helperClass));
		if (!helperClass) {
			validateFixtureCounts(unit, testClassName, violations);
		}
		return violations;
	}

	private String testClassName(CompilationUnit unit) {
		String simpleName = unit.getTypes()
				.stream()
				.filter(type -> type.isClassOrInterfaceDeclaration())
				.map(type -> type.asClassOrInterfaceDeclaration().getNameAsString())
				.findFirst()
				.orElse("<unknown>");
		return unit.getPackageDeclaration()
				.map(declaration -> declaration.getNameAsString() + "." + simpleName)
				.orElse(simpleName);
	}

	private void validateImports(CompilationUnit unit, String testClassName, List<TestCodeViolation> violations) {
		for (ImportDeclaration imported : unit.getImports()) {
			if (imported.isAsterisk()) {
				classViolation(violations, testClassName, "Wildcard import: " + formatImportDeclaration(imported), formatImportDeclaration(imported));
			}
		}
	}

	private static String formatImportDeclaration(ImportDeclaration declaration) {
		StringBuilder text = new StringBuilder("import ");
		if (declaration.isStatic()) {
			text.append("static ");
		}
		text.append(declaration.getNameAsString());
		if (declaration.isAsterisk()) {
			text.append(".*");
		}
		text.append(";");
		return text.toString();
	}

	private void validateTopLevelClasses(CompilationUnit unit, String testClassName, List<TestCodeViolation> violations) {
		long topLevelClasses = unit.getTypes().stream().filter(type -> type.isClassOrInterfaceDeclaration()).count();
		if (topLevelClasses > 1) {
			classViolation(violations, testClassName, "Multiple top-level classes in the same file", unit.toString());
		}
	}

	private void validateClass(ClassOrInterfaceDeclaration type, String testClassName, List<TestCodeViolation> violations) {
		if (isNested(type)) {
			classViolation(violations, testClassName, "Nested class: " + type.getNameAsString(), type);
		}
		if (!type.getExtendedTypes().isEmpty()) {
			classViolation(violations, testClassName, "Test class inheritance: " + type.getNameAsString(), type);
		}
		if (type.isAbstract()) {
			classViolation(violations, testClassName, "Abstract test class: " + type.getNameAsString(), type);
		}
		if (!type.getTypeParameters().isEmpty()) {
			classViolation(violations, testClassName, "Generic test class: " + type.getNameAsString(), type);
		}
		type.getAnnotations().forEach(annotation -> classViolation(violations, testClassName, "Class annotation: " + annotation, annotation));
		type.findAll(InitializerDeclaration.class).forEach(initializer -> classViolation(violations, testClassName, "Class initializer in: " + type.getNameAsString(), initializer));
		type.findAll(ConstructorDeclaration.class).forEach(constructor -> classViolation(violations, testClassName, "Explicit constructor: " + constructor.getNameAsString(), constructor));
	}

	private boolean isNested(ClassOrInterfaceDeclaration type) {
		return type.getParentNode().filter(parent -> parent instanceof ClassOrInterfaceDeclaration).isPresent();
	}

	private boolean isNestedRecord(RecordDeclaration type) {
		return type.getParentNode().filter(parent -> parent instanceof ClassOrInterfaceDeclaration).isPresent();
	}

	private static String fieldVariableNames(FieldDeclaration field) {
		return field.getVariables().stream().map(v -> v.getNameAsString()).collect(Collectors.joining(", "));
	}

	private void validateField(FieldDeclaration field, String testClassName, List<TestCodeViolation> violations) {
		if (field.isStatic()) {
			String names = fieldVariableNames(field);
			classViolation(violations, testClassName, "Static field: " + names, field);
		}
		field.getAnnotations().forEach(annotation -> classViolation(violations, testClassName, "Field annotation: " + annotation, annotation));
		field.getVariables().forEach(variable -> {
			if (variable.getInitializer().isPresent()) {
				String name = variable.getNameAsString();
				classViolation(violations, testClassName, "Field initialization: " + name, field);
			}
		});
	}

	private void validateMethod(MethodDeclaration method, String testClassName, List<TestCodeViolation> violations, boolean helperClass) {
		if (!helperClass) {
			validateAnnotations(method, testClassName, violations);
		}
		if (isLifecycleMethod(method)) {
			classViolation(violations, testClassName, "Lifecycle method: " + method.getNameAsString(), method);
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.empty(), violations));
		} else if (isTestMethod(method)) {
			validateTestMethod(method, testClassName, violations);
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.of(method.getNameAsString()), violations));
		} else if (!helperClass && isUnsupportedTestMethod(method)) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Test method annotation: " + unsupportedTestAnnotation(method), method);
			validateTestMethod(method, testClassName, violations);
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.of(method.getNameAsString()), violations));
		} else if (isFixtureMethod(method)) {
			validateFixtureMethod(method, testClassName, violations);
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.empty(), violations));
		} else if (helperClass) {
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.empty(), violations));
		} else {
			classViolation(violations, testClassName, "Helper method: " + method.getNameAsString(), method);
			method.getBody().ifPresent(body -> validateNodeBody(body, testClassName, Optional.empty(), violations));
		}
	}

	private void validateAnnotations(MethodDeclaration method, String testClassName, List<TestCodeViolation> violations) {
		Set<String> seen = new HashSet<>();
		for (AnnotationExpr annotation : method.getAnnotations()) {
			String name = simpleName(annotation);
			if (!seen.add(name)) {
				methodViolation(violations, testClassName, method.getNameAsString(), "Repeated annotation: " + annotation, annotation);
			}
			if (!SUPPORTED_METHOD_ANNOTATIONS.contains(name)) {
				if (isTestMethod(method) || isUnsupportedTestMethod(method)) {
					methodViolation(violations, testClassName, method.getNameAsString(), "Method annotation: " + annotation, annotation);
				} else {
					classViolation(violations, testClassName, "Method annotation: " + annotation, annotation);
				}
			}
			if ("Test".equals(name) && !annotation.isMarkerAnnotationExpr()) {
				methodViolation(violations, testClassName, method.getNameAsString(), "@Test attributes: " + annotation, annotation);
			}
			if (("Before".equals(name) || "BeforeEach".equals(name)) && !annotation.isMarkerAnnotationExpr()) {
				classViolation(violations, testClassName, "Fixture annotation attributes: " + annotation, annotation);
			}
		}
	}

	private void validateTestMethod(MethodDeclaration method, String testClassName, List<TestCodeViolation> violations) {
		if (!method.getParameters().isEmpty()) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Test method with parameters: " + method.getNameAsString(), method);
		}
		if (!method.getType().isVoidType()) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Test method return type: " + method.getNameAsString(), method);
		}
		if (method.isPrivate()) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Private test method: " + method.getNameAsString(), method);
		}
		if (method.isStatic()) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Static test method: " + method.getNameAsString(), method);
		}
		if (method.getBody().isEmpty()) {
			methodViolation(violations, testClassName, method.getNameAsString(), "Test method without body: " + method.getNameAsString(), method);
		}
	}

	private void validateFixtureMethod(MethodDeclaration method, String testClassName, List<TestCodeViolation> violations) {
		if (method.isStatic()) {
			classViolation(violations, testClassName, "Static fixture method: " + method.getNameAsString(), method);
		}
		if (!method.getParameters().isEmpty()) {
			classViolation(violations, testClassName, "Fixture method with parameters: " + method.getNameAsString(), method);
		}
		if (!method.getType().isVoidType()) {
			classViolation(violations, testClassName, "Fixture method return type: " + method.getNameAsString(), method);
		}
	}

	private void validateFixtureCounts(CompilationUnit unit, String testClassName, List<TestCodeViolation> violations) {
		long beforeFixtures = countMethodsAnnotatedWith(unit, "Before") + countMethodsAnnotatedWith(unit, "BeforeEach");
		if (beforeFixtures > 1) {
			classViolation(violations, testClassName, "Multiple @Before fixtures", unit.toString());
		}
	}

	private long countMethodsAnnotatedWith(CompilationUnit unit, String annotationName) {
		return unit.findAll(MethodDeclaration.class).stream().filter(method -> hasAnnotation(method, annotationName)).count();
	}

	private void validateNodeBody(Node node, String testClassName, Optional<String> testMethodName, List<TestCodeViolation> violations) {
		node.findAll(LambdaExpr.class).stream()
				.filter(lambda -> !hasAssertionMethodAncestor(lambda))
				.forEach(lambda -> violation(violations, testClassName, testMethodName, "Lambda expression", lambda));
		node.findAll(MethodReferenceExpr.class).stream()
				.filter(reference -> !hasAssertionMethodAncestor(reference))
				.forEach(reference -> violation(violations, testClassName, testMethodName, "Method reference", reference));
		node.findAll(ObjectCreationExpr.class).stream()
				.filter(creation -> creation.getAnonymousClassBody().isPresent())
				.forEach(creation -> violation(violations, testClassName, testMethodName, "Anonymous class", creation));
		node.findAll(ClassOrInterfaceDeclaration.class).forEach(type -> violation(violations, testClassName, testMethodName, "Local class: " + type.getNameAsString(), type));
		node.findAll(LocalRecordDeclarationStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Local record: " + statement.getRecordDeclaration().getNameAsString(), statement));
		node.findAll(ForStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "For loop", statement));
		node.findAll(ForEachStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "For-each loop", statement));
		node.findAll(WhileStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "While loop", statement));
		node.findAll(DoStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Do-while loop", statement));
		node.findAll(TryStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Try statement", statement));
		node.findAll(SwitchStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Switch statement", statement));
		node.findAll(SynchronizedStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Synchronized block", statement));
		node.findAll(LabeledStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Labeled statement", statement));
		node.findAll(BreakStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Break statement", statement));
		node.findAll(ContinueStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Continue statement", statement));
		node.findAll(ThrowStmt.class).forEach(statement -> violation(violations, testClassName, testMethodName, "Explicit throw statement", statement));
		node.findAll(ThisExpr.class).forEach(expression -> violation(violations, testClassName, testMethodName, "Explicit this expression", expression));
		node.findAll(SuperExpr.class).forEach(expression -> violation(violations, testClassName, testMethodName, "Explicit super expression", expression));
	}

	private boolean hasAssertionMethodAncestor(Node node) {
		Optional<Node> current = node.getParentNode();
		while (current.isPresent()) {
			Node parent = current.get();
			if (parent instanceof MethodCallExpr && AssertionMethodCalls.isAssertionMethod((MethodCallExpr) parent)) {
				return true;
			}
			current = parent.getParentNode();
		}
		return false;
	}

	private void classViolation(List<TestCodeViolation> violations, String testClassName, String description) {
		classViolation(violations, testClassName, description, "");
	}

	private void classViolation(List<TestCodeViolation> violations, String testClassName, String description, Node node) {
		classViolation(violations, testClassName, description, snippet(node));
	}

	private void classViolation(List<TestCodeViolation> violations, String testClassName, String description, String codeSnippet) {
		violations.add(new TestCodeViolation(ViolationScope.TEST_CLASS, testClassName, Optional.empty(), description, codeSnippet));
	}

	private void methodViolation(List<TestCodeViolation> violations, String testClassName, String testMethodName, String description) {
		methodViolation(violations, testClassName, testMethodName, description, "");
	}

	private void methodViolation(List<TestCodeViolation> violations, String testClassName, String testMethodName, String description, Node node) {
		methodViolation(violations, testClassName, testMethodName, description, snippet(node));
	}

	private void methodViolation(List<TestCodeViolation> violations, String testClassName, String testMethodName, String description, String codeSnippet) {
		violations.add(new TestCodeViolation(ViolationScope.TEST_METHOD, testClassName, testMethodName, description, codeSnippet));
	}

	private void violation(List<TestCodeViolation> violations, String testClassName, Optional<String> testMethodName, String description) {
		violation(violations, testClassName, testMethodName, description, "");
	}

	private void violation(List<TestCodeViolation> violations, String testClassName, Optional<String> testMethodName, String description, Node node) {
		violation(violations, testClassName, testMethodName, description, snippet(node));
	}

	private void violation(List<TestCodeViolation> violations, String testClassName, Optional<String> testMethodName, String description, String codeSnippet) {
		if (testMethodName.isPresent()) {
			methodViolation(violations, testClassName, testMethodName.get(), description, codeSnippet);
		} else {
			classViolation(violations, testClassName, description, codeSnippet);
		}
	}

	private String snippet(Node node) {
		return node.toString().trim();
	}

	private boolean hasAnnotation(MethodDeclaration method, String annotationName) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(annotationName::equals);
	}

	private boolean isTestMethod(MethodDeclaration method) {
		return hasAnnotation(method, "Test");
	}

	private boolean isUnsupportedTestMethod(MethodDeclaration method) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(TEST_METHOD_ONLY_UNSUPPORTED_ANNOTATIONS::contains);
	}

	private boolean isLifecycleMethod(MethodDeclaration method) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(LIFECYCLE_ANNOTATIONS::contains);
	}

	private boolean isFixtureMethod(MethodDeclaration method) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(name -> Set.of("Before", "BeforeEach").contains(name));
	}

	private String unsupportedTestAnnotation(MethodDeclaration method) {
		return method.getAnnotations()
				.stream()
				.map(this::simpleName)
				.filter(TEST_METHOD_ONLY_UNSUPPORTED_ANNOTATIONS::contains)
				.findFirst()
				.orElse("<unknown>");
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
