package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;

public final class ExtractableArrangeRuns {

	private static final StatementDependencyAnalyzer ANALYZER = new StatementDependencyAnalyzer();

	private ExtractableArrangeRuns() {
	}

	public static List<ExtractableArrangeRun> nWay(List<TestCase> testCases, int minimumLength) {
		return nWay(testCases, minimumLength, new Session());
	}

	public static List<ExtractableArrangeRun> nWay(List<TestCase> testCases, int minimumLength, Session session) {
		Objects.requireNonNull(session);
		if (testCases.size() < 2 || minimumLength < 1) {
			return List.of();
		}
		List<List<Optional<StatementDependencyAnalyzer.Analysis>>> analysesByTest = new ArrayList<>();
		for (TestCase testCase : testCases) {
			analysesByTest.add(session.analysesByMethod.computeIfAbsent(methodId(testCase), id -> analyses(testCase.body().statements())));
		}
		Map<List<String>, List<Window>> windowsByText = new LinkedHashMap<>();
		for (int testIndex = 0; testIndex < testCases.size(); testIndex++) {
			List<CodeStatement> arrange = ArrangeProjection.arrangeStatements(testCases.get(testIndex));
			for (int start = 0; start < arrange.size(); start++) {
				for (int length = arrange.size() - start; length >= minimumLength; length--) {
					List<CodeStatement> window = List.copyOf(arrange.subList(start, start + length));
					List<String> texts = window.stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
					windowsByText.computeIfAbsent(texts, key -> new ArrayList<>()).add(new Window(testIndex, start, window));
				}
			}
		}
		List<ExtractableArrangeRun> candidates = new ArrayList<>();
		for (List<Window> windows : windowsByText.values()) {
			if (windows.stream().map(window -> window.testIndex).distinct().count() < 2) {
				continue;
			}
			List<Occurrence> occurrences = new ArrayList<>();
			for (Window window : windows) {
				if (occurrences.stream().anyMatch(occurrence -> occurrence.testIndex == window.testIndex)) {
					continue;
				}
				Optional<Extractability> extractability = session.extractabilityByWindow.computeIfAbsent(
						List.of(methodId(testCases.get(window.testIndex)), window.start, window.window.size()),
						key -> extractable(
								testCases.get(window.testIndex),
								window.start,
								window.window.size(),
								analysesByTest.get(window.testIndex)));
				if (extractability.isPresent()) {
					occurrences.add(new Occurrence(window.testIndex, window.start, window.window, extractability.get()));
				}
			}
			sharedRun(testCases.size(), occurrences).ifPresent(candidates::add);
		}
		candidates.sort(Comparator
				.comparingInt(ExtractableArrangeRun::length).reversed()
				.thenComparingInt(run -> -run.participantCount()));
		List<ExtractableArrangeRun> selected = new ArrayList<>();
		for (ExtractableArrangeRun candidate : candidates) {
			if (selected.stream().noneMatch(existing -> overlapsAnyTest(existing, candidate, testCases.size()))) {
				selected.add(candidate);
			}
		}
		return List.copyOf(selected);
	}

	public static Optional<Extractability> extractable(TestCase testCase, int start, int length) {
		return extractable(testCase, start, length, analyses(testCase.body().statements()));
	}

	private static Optional<Extractability> extractable(
			TestCase testCase,
			int start,
			int length,
			List<Optional<StatementDependencyAnalyzer.Analysis>> analyses) {
		List<CodeStatement> body = testCase.body().statements();
		int arrangeEnd = arrangeEnd(body);
		if (start < 0 || length < 1 || start + length > arrangeEnd) {
			return Optional.empty();
		}
		if (analyses.subList(start, start + length).stream().anyMatch(Optional::isEmpty)) {
			return Optional.empty();
		}
		Map<String, String> typesAtStart = typesBefore(testCase, analyses, start);
		List<NamedTypedVariable> liveIns = new ArrayList<>();
		Set<String> definedInWindow = new LinkedHashSet<>();
		Set<String> declaredInWindow = new LinkedHashSet<>();
		for (int index = start; index < start + length; index++) {
			StatementDependencyAnalyzer.Analysis analysis = analyses.get(index).orElseThrow();
			for (String used : analysis.uses()) {
				if (!definedInWindow.contains(used) && liveIns.stream().noneMatch(variable -> variable.name().equals(used))) {
					String type = typesAtStart.get(used);
					if (type == null) {
						return Optional.empty();
					}
					liveIns.add(new NamedTypedVariable(used, type));
				}
			}
			definedInWindow.addAll(analysis.definitions());
			declaredInWindow.addAll(analysis.declaredTypes().keySet());
			typesAtStart.putAll(analysis.declaredTypes());
		}
		List<NamedTypedVariable> liveOuts = new ArrayList<>();
		for (String defined : definedInWindow) {
			if (usedAfter(analyses, start + length, defined)) {
				String type = typesAtStart.get(defined);
				if (type == null) {
					return Optional.empty();
				}
				liveOuts.add(new NamedTypedVariable(defined, type));
			}
		}
		if (liveOuts.size() > 1) {
			return Optional.empty();
		}
		return Optional.of(new Extractability(liveIns, liveOuts.stream().findFirst(), declaredInWindow));
	}

	private static Optional<ExtractableArrangeRun> sharedRun(int testCount, List<Occurrence> occurrences) {
		Set<Integer> tests = occurrences.stream().map(occurrence -> occurrence.testIndex).collect(Collectors.toCollection(LinkedHashSet::new));
		if (tests.size() < 2) {
			return Optional.empty();
		}
		List<Integer> starts = new ArrayList<>();
		for (int index = 0; index < testCount; index++) {
			starts.add(-1);
		}
		List<Extractability> extractabilities = new ArrayList<>();
		for (Occurrence occurrence : occurrences) {
			if (starts.get(occurrence.testIndex) >= 0) {
				continue;
			}
			starts.set(occurrence.testIndex, occurrence.start);
			extractabilities.add(occurrence.extractability);
		}
		return unify(occurrences.get(0).window, starts, extractabilities);
	}

	private static Optional<ExtractableArrangeRun> unify(List<CodeStatement> statements, List<Integer> starts, List<Extractability> extractabilities) {
		List<NamedTypedVariable> liveIns = extractabilities.get(0).liveIns();
		for (Extractability extractability : extractabilities) {
			if (!extractability.liveIns().equals(liveIns)) {
				return Optional.empty();
			}
		}
		Optional<NamedTypedVariable> liveOut = Optional.empty();
		for (Extractability extractability : extractabilities) {
			if (extractability.liveOut().isPresent()) {
				if (liveOut.isPresent() && !liveOut.get().equals(extractability.liveOut().get())) {
					return Optional.empty();
				}
				liveOut = extractability.liveOut();
			}
		}
		return Optional.of(new ExtractableArrangeRun(statements, starts, liveIns, liveOut));
	}

	private static boolean overlapsAnyTest(ExtractableArrangeRun left, ExtractableArrangeRun right, int testCount) {
		for (int index = 0; index < testCount; index++) {
			if (left.overlaps(right, index)) {
				return true;
			}
		}
		return false;
	}

	private static int arrangeEnd(List<CodeStatement> body) {
		for (int index = 0; index < body.size(); index++) {
			if (body.get(index).isAssertion()) {
				return index;
			}
		}
		return body.size();
	}

	private static String methodId(TestCase testCase) {
		return testCase.sourceTestClass()
				.map(source -> source.qualifiedName() + "#" + testCase.name())
				.orElseGet(() -> System.identityHashCode(testCase) + "#" + testCase.name());
	}

	private static List<Optional<StatementDependencyAnalyzer.Analysis>> analyses(List<CodeStatement> statements) {
		List<Optional<StatementDependencyAnalyzer.Analysis>> analyses = new ArrayList<>();
		for (CodeStatement statement : statements) {
			analyses.add(ANALYZER.analyze(statement));
		}
		return analyses;
	}

	private static Map<String, String> typesBefore(TestCase testCase, List<Optional<StatementDependencyAnalyzer.Analysis>> analyses, int start) {
		Map<String, String> types = new LinkedHashMap<>();
		testCase.sourceTestClass().map(TestClass::fields).orElse(List.of()).forEach(field -> types.put(field.name(), field.type()));
		for (int index = 0; index < start; index++) {
			analyses.get(index).ifPresent(analysis -> types.putAll(analysis.declaredTypes()));
		}
		return types;
	}

	private static boolean usedAfter(List<Optional<StatementDependencyAnalyzer.Analysis>> analyses, int from, String name) {
		for (int index = from; index < analyses.size(); index++) {
			Optional<StatementDependencyAnalyzer.Analysis> analysis = analyses.get(index);
			if (analysis.isPresent() && analysis.get().uses().contains(name)) {
				return true;
			}
		}
		return false;
	}

	public static final class Session {

		private final Map<String, List<Optional<StatementDependencyAnalyzer.Analysis>>> analysesByMethod = new HashMap<>();
		private final Map<List<?>, Optional<Extractability>> extractabilityByWindow = new HashMap<>();
	}

	public static final class Extractability {

		private final List<NamedTypedVariable> liveIns;
		private final Optional<NamedTypedVariable> liveOut;
		private final Set<String> declaredInWindow;

		private Extractability(List<NamedTypedVariable> liveIns, Optional<NamedTypedVariable> liveOut, Set<String> declaredInWindow) {
			this.liveIns = List.copyOf(liveIns);
			this.liveOut = liveOut;
			this.declaredInWindow = Set.copyOf(declaredInWindow);
		}

		public List<NamedTypedVariable> liveIns() {
			return liveIns;
		}

		public Optional<NamedTypedVariable> liveOut() {
			return liveOut;
		}

		public boolean declares(String name) {
			return declaredInWindow.contains(name);
		}
	}

	private static final class Window {

		private final int testIndex;
		private final int start;
		private final List<CodeStatement> window;

		private Window(int testIndex, int start, List<CodeStatement> window) {
			this.testIndex = testIndex;
			this.start = start;
			this.window = window;
		}
	}

	private static final class Occurrence {

		private final int testIndex;
		private final int start;
		private final List<CodeStatement> window;
		private final Extractability extractability;

		private Occurrence(int testIndex, int start, List<CodeStatement> window, Extractability extractability) {
			this.testIndex = testIndex;
			this.start = start;
			this.window = window;
			this.extractability = extractability;
		}
	}
}
