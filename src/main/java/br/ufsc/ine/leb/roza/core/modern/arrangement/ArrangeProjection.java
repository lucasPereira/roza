package br.ufsc.ine.leb.roza.core.modern.arrangement;

import java.util.List;
import java.util.stream.Collectors;

import br.ufsc.ine.leb.roza.core.modern.decomposition.TestCase;
import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;

public final class ArrangeProjection {

	public static List<CodeStatement> arrangeStatements(TestCase testCase) {
		return testCase.body()
				.statements()
				.stream()
				.takeWhile(statement -> !statement.isAssertion())
				.collect(Collectors.toList());
	}

	public static List<String> normalizedStatements(TestCase testCase) {
		return arrangeStatements(testCase)
				.stream()
				.map(CodeStatement::normalizedText)
				.collect(Collectors.toList());
	}
}
