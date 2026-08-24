package br.ufsc.ine.leb.roza.core.modern.refactoring;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.arrangement.ExtractableArrangeRun;
import br.ufsc.ine.leb.roza.core.modern.arrangement.ExtractableArrangeRuns;
import br.ufsc.ine.leb.roza.core.modern.arrangement.NamedTypedVariable;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseCluster;
import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;
import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeBlock;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class DelegatedSetupTestClassRefactorer implements TestClassRefactorer {

	private final int minimumLength;

	public DelegatedSetupTestClassRefactorer() {
		this(1);
	}

	public DelegatedSetupTestClassRefactorer(int minimumLength) {
		if (minimumLength < 1) {
			throw new IllegalArgumentException("Delegated setup minimum length must be at least 1.");
		}
		this.minimumLength = minimumLength;
	}

	@Override
	public RefactoredTestClasses refactor(TestCaseClusters clusters) {
		Map<String, TestClass> originalClasses = new LinkedHashMap<>();
		Map<String, CodeBlock> rewrittenBodies = new LinkedHashMap<>();
		List<TestClass> helpers = new ArrayList<>();
		int helperIndex = 1;
		for (TestCaseCluster cluster : clusters.clusters()) {
			for (TestCase testCase : cluster.testCases()) {
				testCase.sourceTestClass().ifPresent(source -> originalClasses.putIfAbsent(source.qualifiedName(), source));
			}
			if (cluster.size() < 2) {
				continue;
			}
			List<ExtractableArrangeRun> runs = ExtractableArrangeRuns.nWay(cluster.testCases(), minimumLength);
			if (runs.isEmpty()) {
				continue;
			}
			String helperName = "HelperClass" + helperIndex;
			helpers.add(helperClass(helperName, imports(cluster.testCases()), runs));
			for (int testIndex = 0; testIndex < cluster.testCases().size(); testIndex++) {
				TestCase testCase = cluster.testCases().get(testIndex);
				TestClass source = testCase.sourceTestClass().orElseThrow(
						() -> new IllegalStateException("Delegated setup requires each test to keep its source class."));
				rewrittenBodies.put(key(source, testCase), replaceRuns(testCase, runs, testIndex, helperName));
			}
			helperIndex++;
		}
		List<TestClass> testClasses = originalClasses.values().stream()
				.map(source -> rewrite(source, rewrittenBodies))
				.collect(Collectors.toList());
		return new RefactoredTestClasses(testClasses, helpers);
	}

	private TestClass rewrite(TestClass source, Map<String, CodeBlock> rewrittenBodies) {
		List<TestMethod> methods = source.testMethods().stream()
				.map(method -> rewrittenBodies.containsKey(key(source, method))
						? new TestMethod(method.name(), method.annotations(), method.thrownExceptions(), rewrittenBodies.get(key(source, method)))
						: method)
				.collect(Collectors.toList());
		return new TestClass(
				source.name(),
				source.packageName().orElse(null),
				source.imports(),
				source.setupAnnotation().orElse(null),
				source.fields(),
				source.fixtures(),
				source.helperMethods(),
				methods);
	}

	private TestClass helperClass(String name, List<String> imports, List<ExtractableArrangeRun> runs) {
		List<HelperMethod> methods = new ArrayList<>();
		int methodIndex = 1;
		for (ExtractableArrangeRun run : runs) {
			methods.add(helperMethod("setup" + methodIndex, run));
			methodIndex++;
		}
		return new TestClass(name, null, imports, null, List.of(), List.of(), methods, List.of());
	}

	private HelperMethod helperMethod(String name, ExtractableArrangeRun run) {
		List<String> parameters = run.liveIns().stream()
				.map(variable -> variable.type() + " " + variable.name())
				.collect(Collectors.toList());
		List<CodeStatement> body = new ArrayList<>(run.statements());
		String returnType = "void";
		if (run.liveOut().isPresent()) {
			NamedTypedVariable liveOut = run.liveOut().get();
			returnType = liveOut.type();
			if (run.declares(liveOut) || body.stream().noneMatch(statement -> statement.normalizedText().contains(liveOut.name() + " =") || statement.normalizedText().startsWith(liveOut.type() + " " + liveOut.name()))) {
				String declaration = liveOut.type() + " " + liveOut.name() + ";";
				boolean alreadyDeclared = body.stream().anyMatch(statement -> statement.normalizedText().startsWith(liveOut.type() + " " + liveOut.name()));
				if (!alreadyDeclared) {
					body.add(0, statement(declaration));
				}
			}
			body.add(statement("return " + liveOut.name() + ";"));
		}
		return new HelperMethod(List.of("public", "static"), returnType, name, parameters, List.of(), new CodeBlock(body));
	}

	private CodeBlock replaceRuns(TestCase testCase, List<ExtractableArrangeRun> runs, int testIndex, String helperName) {
		List<CodeStatement> statements = new ArrayList<>(testCase.body().statements());
		List<ExtractableArrangeRun> ordered = new ArrayList<>(runs);
		ordered.sort((left, right) -> Integer.compare(right.startFor(testIndex), left.startFor(testIndex)));
		Map<ExtractableArrangeRun, Integer> methodIndexes = new LinkedHashMap<>();
		for (int index = runs.size() - 1; index >= 0; index--) {
			methodIndexes.put(runs.get(index), index + 1);
		}
		for (ExtractableArrangeRun run : ordered) {
			int start = run.startFor(testIndex);
			List<CodeStatement> replacement = List.of(call(helperName, "setup" + methodIndexes.get(run), run));
			statements.subList(start, start + run.length()).clear();
			statements.addAll(start, replacement);
		}
		return new CodeBlock(statements);
	}

	private CodeStatement call(String helperName, String methodName, ExtractableArrangeRun run) {
		String arguments = run.liveIns().stream().map(NamedTypedVariable::name).collect(Collectors.joining(", "));
		String invocation = helperName + "." + methodName + "(" + arguments + ");";
		if (run.liveOut().isEmpty()) {
			return statement(invocation);
		}
		NamedTypedVariable liveOut = run.liveOut().get();
		if (run.declares(liveOut)) {
			return statement(liveOut.type() + " " + liveOut.name() + " = " + helperName + "." + methodName + "(" + arguments + ");");
		}
		return statement(liveOut.name() + " = " + helperName + "." + methodName + "(" + arguments + ");");
	}

	private List<String> imports(List<TestCase> testCases) {
		Set<String> imports = new LinkedHashSet<>();
		for (TestCase testCase : testCases) {
			testCase.sourceTestClass().ifPresent(source -> imports.addAll(source.imports()));
		}
		return List.copyOf(imports);
	}

	private String key(TestClass source, TestCase testCase) {
		return source.qualifiedName() + "#" + testCase.name();
	}

	private String key(TestClass source, TestMethod method) {
		return source.qualifiedName() + "#" + method.name();
	}

	private CodeStatement statement(String text) {
		return new CodeStatement(text, text);
	}
}
