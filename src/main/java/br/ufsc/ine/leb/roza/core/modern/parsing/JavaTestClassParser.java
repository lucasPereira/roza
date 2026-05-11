package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.printer.PrettyPrinterConfiguration;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

public final class JavaTestClassParser implements TestClassParser {

	private final UnsupportedFeaturePolicy unsupportedFeaturePolicy;
	private final JavaUnsupportedFeatureValidator validator;
	private final List<UnsupportedFeatureDiagnostic> diagnostics;

	public JavaTestClassParser() {
		this(UnsupportedFeaturePolicy.SAFE);
	}

	public JavaTestClassParser(UnsupportedFeaturePolicy unsupportedFeaturePolicy) {
		this.unsupportedFeaturePolicy = Objects.requireNonNull(unsupportedFeaturePolicy);
		validator = new JavaUnsupportedFeatureValidator();
		diagnostics = new ArrayList<>();
	}

	@Override
	public ParsedTestClasses parse(LoadedCodeFiles codeFiles) {
		diagnostics.clear();
		List<TestClass> testClasses = new ArrayList<>();
		for (CodeFile codeFile : codeFiles.codeFiles()) {
			CompilationUnit unit = JavaParser.parse(codeFile.content());
			if (containsNoTests(unit)) {
				continue;
			}
			if (hasUnsupportedFeatures(unit)) {
				continue;
			}
			extractTestClass(unit).ifPresent(testClasses::add);
		}
		return new ParsedTestClasses(testClasses);
	}

	public List<UnsupportedFeatureDiagnostic> diagnostics() {
		return List.copyOf(diagnostics);
	}

	private boolean containsNoTests(CompilationUnit unit) {
		return unit.findAll(MethodDeclaration.class).stream().noneMatch(this::isTestLikeMethod);
	}

	private boolean hasUnsupportedFeatures(CompilationUnit unit) {
		List<UnsupportedFeatureDiagnostic> unsupportedFeatures = validator.validate(unit);
		if (unsupportedFeatures.isEmpty()) {
			return false;
		}
		if (unsupportedFeaturePolicy == UnsupportedFeaturePolicy.SAFE) {
			throw new UnsupportedFeatureException(unsupportedFeatures.get(0));
		}
		diagnostics.addAll(unsupportedFeatures);
		return true;
	}

	private Optional<TestClass> extractTestClass(CompilationUnit unit) {
		return unit.getTypes()
				.stream()
				.filter(type -> type.isClassOrInterfaceDeclaration())
				.map(type -> type.asClassOrInterfaceDeclaration())
				.findFirst()
				.map(this::extractTestClass);
	}

	private TestClass extractTestClass(ClassOrInterfaceDeclaration parsedClass) {
		List<Field> fields = parsedClass.getFields().stream().flatMap(field -> extractFields(field).stream()).collect(Collectors.toList());
		List<FixtureMethod> fixtures = parsedClass.getMethods().stream().filter(this::isFixtureMethod).map(this::extractFixture).collect(Collectors.toList());
		List<HelperMethod> helperMethods = parsedClass.getMethods().stream().filter(method -> !isTestMethod(method)).filter(method -> !isFixtureMethod(method)).map(this::extractHelper).collect(Collectors.toList());
		List<TestMethod> testMethods = parsedClass.getMethods().stream().filter(this::isTestMethod).map(this::extractTestMethod).collect(Collectors.toList());
		return new TestClass(parsedClass.getNameAsString(), fields, fixtures, helperMethods, testMethods);
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
		return new CodeBlock(parsedMethod.getBody().orElseThrow().getStatements().stream().map(this::statement).collect(Collectors.toList()));
	}

	private CodeStatement statement(Statement statement) {
		return new CodeStatement(statement.toString(), normalize(statement));
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
