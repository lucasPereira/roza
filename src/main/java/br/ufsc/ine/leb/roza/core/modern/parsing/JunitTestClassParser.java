package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

public final class JunitTestClassParser implements TestClassParser {

	private final JavaUnsupportedFeatureValidator validator;
	private final List<TestCodeViolation> diagnostics;

	public JunitTestClassParser() {
		this(UnsupportedFeaturePolicy.SAFE);
	}

	public JunitTestClassParser(UnsupportedFeaturePolicy unsupportedFeaturePolicy) {
		Objects.requireNonNull(unsupportedFeaturePolicy);
		validator = new JavaUnsupportedFeatureValidator();
		diagnostics = new ArrayList<>();
	}

	@Override
	public ParsedTestClasses parse(LoadedCodeFiles codeFiles) {
		diagnostics.clear();
		List<TestClass> testClasses = new ArrayList<>();
		List<TestCodeViolation> violations = new ArrayList<>();
		for (CodeFile codeFile : codeFiles.codeFiles()) {
			try {
				CompilationUnit unit = JavaParser.parse(codeFile.content());
				if (containsNoTests(unit)) {
					continue;
				}
				List<TestCodeViolation> codeFileViolations = validator.validate(unit);
				diagnostics.addAll(codeFileViolations);
				violations.addAll(codeFileViolations);
				extractTestClass(unit).ifPresent(testClasses::add);
			} catch (UnsupportedFeatureException exception) {
				throw exception;
			} catch (RuntimeException exception) {
				String detail = exception.getMessage() != null ? exception.getMessage() : exception.toString();
				throw new ParsingException(codeFile.source(), detail, exception);
			}
		}
		return new ParsedTestClasses(testClasses, violations);
	}

	public List<TestCodeViolation> diagnostics() {
		return List.copyOf(diagnostics);
	}

	private boolean containsNoTests(CompilationUnit unit) {
		return unit.findAll(MethodDeclaration.class).stream().noneMatch(this::isTestLikeMethod);
	}

	private Optional<TestClass> extractTestClass(CompilationUnit unit) {
		return unit.getTypes()
				.stream()
				.filter(type -> type.isClassOrInterfaceDeclaration())
				.map(type -> type.asClassOrInterfaceDeclaration())
				.findFirst()
				.map(parsedClass -> extractTestClass(unit, parsedClass));
	}

	private TestClass extractTestClass(CompilationUnit unit, ClassOrInterfaceDeclaration parsedClass) {
		List<String> imports = imports(unit);
		List<Field> fields = parsedClass.getFields().stream().flatMap(field -> extractFields(field).stream()).collect(Collectors.toList());
		List<FixtureMethod> fixtures = parsedClass.getMethods().stream().filter(this::isFixtureMethod).map(this::extractFixture).collect(Collectors.toList());
		List<HelperMethod> helperMethods = parsedClass.getMethods().stream().filter(method -> !isTestMethod(method)).filter(method -> !isFixtureMethod(method)).map(this::extractHelper).collect(Collectors.toList());
		List<TestMethod> testMethods = parsedClass.getMethods().stream().filter(this::isTestMethod).map(this::extractTestMethod).collect(Collectors.toList());
		return new TestClass(parsedClass.getNameAsString(), packageName(unit).orElse(null), imports, setupAnnotation(imports, fixtures, testMethods), fields, fixtures, helperMethods, testMethods);
	}

	private Optional<String> packageName(CompilationUnit unit) {
		return unit.getPackageDeclaration().map(declaration -> declaration.getNameAsString());
	}

	private List<String> imports(CompilationUnit unit) {
		return unit.getImports().stream().map(this::formatImportDeclaration).collect(Collectors.toList());
	}

	private String formatImportDeclaration(ImportDeclaration declaration) {
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

	private SetupAnnotation setupAnnotation(List<String> imports, List<FixtureMethod> fixtures, List<TestMethod> testMethods) {
		Optional<FixtureMethod> fixture = fixtures.stream().filter(method -> method.kind() == FixtureKind.BEFORE).findFirst();
		if (fixture.isPresent()) {
			CodeAnnotation annotation = fixture.get().annotations().stream()
					.filter(this::isSetupAnnotation)
					.findFirst()
					.orElseThrow();
			return new SetupAnnotation(annotation, importForExistingAnnotation(imports, annotation));
		}
		if (usesJupiterTest(imports, testMethods)) {
			return new SetupAnnotation(new CodeAnnotation("BeforeEach", "@BeforeEach"), Optional.of("import org.junit.jupiter.api.BeforeEach;"));
		}
		return new SetupAnnotation(new CodeAnnotation("Before", "@Before"), Optional.of("import org.junit.Before;"));
	}

	private boolean isSetupAnnotation(CodeAnnotation annotation) {
		return "Before".equals(annotation.name()) || "BeforeEach".equals(annotation.name());
	}

	private Optional<String> importForExistingAnnotation(List<String> imports, CodeAnnotation annotation) {
		if (annotation.text().contains(".")) {
			return Optional.empty();
		}
		String suffix = "." + annotation.name() + ";";
		return imports.stream().filter(imported -> imported.endsWith(suffix)).findFirst();
	}

	private boolean usesJupiterTest(List<String> imports, List<TestMethod> testMethods) {
		if (imports.contains("import org.junit.jupiter.api.Test;")) {
			return true;
		}
		return testMethods.stream()
				.flatMap(method -> method.annotations().stream())
				.anyMatch(annotation -> "Test".equals(annotation.name()) && annotation.text().contains("org.junit.jupiter.api.Test"));
	}

	private List<Field> extractFields(FieldDeclaration parsedField) {
		List<String> modifiers = modifiers(parsedField.getModifiers());
		List<Field> fields = new ArrayList<>();
		for (VariableDeclarator variable : parsedField.getVariables()) {
			Optional<CodeStatement> initialization = variable.getInitializer().map(this::expression);
			fields.add(new Field(modifiers, variable.getType().asString(), variable.getNameAsString(), initialization));
		}
		return fields;
	}

	private FixtureMethod extractFixture(MethodDeclaration parsedMethod) {
		return new FixtureMethod(fixtureKind(parsedMethod), parsedMethod.getNameAsString(), annotations(parsedMethod.getAnnotations()), body(parsedMethod));
	}

	private FixtureKind fixtureKind(MethodDeclaration parsedMethod) {
		if (hasAnnotation(parsedMethod, "Before") || hasAnnotation(parsedMethod, "BeforeEach")) {
			return FixtureKind.BEFORE;
		}
		return FixtureKind.AFTER;
	}

	private HelperMethod extractHelper(MethodDeclaration parsedMethod) {
		return new HelperMethod(
				modifiers(parsedMethod.getModifiers()),
				parsedMethod.getType().asString(),
				parsedMethod.getNameAsString(),
				parsedMethod.getParameters().stream().map(Object::toString).collect(Collectors.toList()),
				annotations(parsedMethod.getAnnotations()),
				body(parsedMethod));
	}

	private TestMethod extractTestMethod(MethodDeclaration parsedMethod) {
		return new TestMethod(parsedMethod.getNameAsString(), annotations(parsedMethod.getAnnotations()), body(parsedMethod));
	}

	private CodeBlock body(MethodDeclaration parsedMethod) {
		return parsedMethod.getBody()
				.map(body -> new CodeBlock(body.getStatements().stream().map(this::statement).collect(Collectors.toList())))
				.orElseGet(() -> new CodeBlock(List.of()));
	}

	private CodeStatement statement(Statement statement) {
		return new CodeStatement(statement.toString(), normalize(statement), isAssertion(statement));
	}

	private CodeStatement expression(Expression expression) {
		return new CodeStatement(expression.toString(), normalize(expression));
	}

	private String normalize(Statement statement) {
		return statement.toString(prettyPrinterConfiguration()).trim();
	}

	private String normalize(Expression expression) {
		return expression.toString(prettyPrinterConfiguration()).trim();
	}

	private boolean isAssertion(Statement statement) {
		return statement.findAll(MethodCallExpr.class).stream().anyMatch(AssertionMethodCalls::isAssertionMethod);
	}

	private PrettyPrinterConfiguration prettyPrinterConfiguration() {
		PrettyPrinterConfiguration configuration = new PrettyPrinterConfiguration();
		configuration.setEndOfLineCharacter(" ");
		configuration.setIndentSize(0);
		configuration.setPrintComments(false);
		return configuration;
	}

	private List<CodeAnnotation> annotations(List<AnnotationExpr> annotations) {
		return annotations.stream().map(annotation -> new CodeAnnotation(simpleName(annotation), annotation.toString())).collect(Collectors.toList());
	}

	private List<String> modifiers(List<Modifier> modifiers) {
		return modifiers.stream().map(Modifier::toString).map(String::trim).filter(modifier -> !modifier.isEmpty()).collect(Collectors.toList());
	}

	private boolean isTestMethod(MethodDeclaration method) {
		return hasAnnotation(method, "Test");
	}

	private boolean isTestLikeMethod(MethodDeclaration method) {
		return method.getAnnotations()
				.stream()
				.map(this::simpleName)
				.anyMatch(name -> List.of("Test", "ParameterizedTest", "Theory", "TestFactory", "TestTemplate", "RepeatedTest").contains(name));
	}

	private boolean isFixtureMethod(MethodDeclaration method) {
		return hasAnnotation(method, "Before") || hasAnnotation(method, "BeforeEach");
	}

	private boolean hasAnnotation(MethodDeclaration method, String name) {
		return method.getAnnotations().stream().map(this::simpleName).anyMatch(name::equals);
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
