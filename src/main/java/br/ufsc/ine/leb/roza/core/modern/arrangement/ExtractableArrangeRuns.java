package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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
		if (testCases.size() < 2 || minimumLength < 1) {
			return List.of();
		}
		int shortestIndex = shortestArrangeIndex(testCases);
		List<CodeStatement> shortest = ArrangeProjection.arrangeStatements(testCases.get(shortestIndex));
		List<ExtractableArrangeRun> candidates = new ArrayList<>();
		for (int start = 0; start < shortest.size(); start++) {
			for (int length = shortest.size() - start; length >= minimumLength; length--) {
				nWayCandidate(testCases, shortestIndex, start, length).ifPresent(candidates::add);
			}
		}
		candidates.sort(Comparator.comparingInt(ExtractableArrangeRun::length).reversed());
		List<ExtractableArrangeRun> selected = new ArrayList<>();
		for (ExtractableArrangeRun candidate : candidates) {
			if (selected.stream().noneMatch(existing -> overlapsAnyTest(existing, candidate, testCases.size()))) {
				selected.add(candidate);
			}
		}
		return List.copyOf(selected);
	}

	public static Optional<Extractability> extractable(TestCase testCase, int start, int length) {
		List<CodeStatement> body = testCase.body().statements();
		int arrangeEnd = arrangeEnd(body);
		if (start < 0 || length < 1 || start + length > arrangeEnd) {
			return Optional.empty();
		}
		List<Optional<StatementDependencyAnalyzer.Analysis>> analyses = analyses(body);
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

	private static Optional<ExtractableArrangeRun> nWayCandidate(List<TestCase> testCases, int shortestIndex, int start, int length) {
		List<CodeStatement> shortest = ArrangeProjection.arrangeStatements(testCases.get(shortestIndex));
		List<CodeStatement> window = shortest.subList(start, start + length);
		List<String> texts = window.stream().map(CodeStatement::normalizedText).collect(Collectors.toList());
		Optional<Extractability> shortestExtractability = extractable(testCases.get(shortestIndex), start, length);
		if (shortestExtractability.isEmpty()) {
			return Optional.empty();
		}
		List<Integer> starts = new ArrayList<>();
		List<Extractability> extractabilities = new ArrayList<>();
		for (int index = 0; index < testCases.size(); index++) {
			if (index == shortestIndex) {
				starts.add(start);
				extractabilities.add(shortestExtractability.get());
				continue;
			}
			Optional<Integer> occurrence = findExtractableOccurrence(testCases.get(index), texts);
			if (occurrence.isEmpty()) {
				return Optional.empty();
			}
			starts.add(occurrence.get());
			extractabilities.add(extractable(testCases.get(index), occurrence.get(), length).orElseThrow());
		}
		return unify(window, starts, extractabilities);
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

	private static Optional<Integer> findExtractableOccurrence(TestCase testCase, List<String> texts) {
		List<CodeStatement> arrange = ArrangeProjection.arrangeStatements(testCase);
		int length = texts.size();
		for (int start = 0; start + length <= arrange.size(); start++) {
			boolean matches = true;
			for (int offset = 0; offset < length; offset++) {
				if (!arrange.get(start + offset).normalizedText().equals(texts.get(offset))) {
					matches = false;
					break;
				}
			}
			if (matches && extractable(testCase, start, length).isPresent()) {
				return Optional.of(start);
			}
		}
		return Optional.empty();
	}

	private static boolean overlapsAnyTest(ExtractableArrangeRun left, ExtractableArrangeRun right, int testCount) {
		for (int index = 0; index < testCount; index++) {
			if (left.overlaps(right, index)) {
				return true;
			}
		}
		return false;
	}

	private static int shortestArrangeIndex(List<TestCase> testCases) {
		int shortestIndex = 0;
		int shortestSize = ArrangeProjection.arrangeStatements(testCases.get(0)).size();
		for (int index = 1; index < testCases.size(); index++) {
			int size = ArrangeProjection.arrangeStatements(testCases.get(index)).size();
			if (size < shortestSize) {
				shortestIndex = index;
				shortestSize = size;
			}
		}
		return shortestIndex;
	}

	private static int arrangeEnd(List<CodeStatement> body) {
		for (int index = 0; index < body.size(); index++) {
			if (body.get(index).isAssertion()) {
				return index;
			}
		}
		return body.size();
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
}
