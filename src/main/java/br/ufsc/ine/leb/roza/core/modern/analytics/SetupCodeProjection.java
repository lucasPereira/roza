package br.ufsc.ine.leb.roza.core.modern.analytics;

import java.util.ArrayList;
import java.util.List;

import br.ufsc.ine.leb.roza.core.modern.parsing.CodeStatement;
import br.ufsc.ine.leb.roza.core.modern.parsing.Field;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureKind;
import br.ufsc.ine.leb.roza.core.modern.parsing.FixtureMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.HelperMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;

public final class SetupCodeProjection {

	public static List<String> statements(List<TestClass> testClasses) {
		List<String> statements = new ArrayList<>();
		for (TestClass testClass : testClasses) {
			for (Field field : testClass.fields()) {
				field.initialization().map(initialization -> fieldInitialization(field, initialization)).ifPresent(statements::add);
			}
			for (FixtureMethod fixture : testClass.fixtures()) {
				if (fixture.kind() == FixtureKind.BEFORE) {
					fixture.body().statements().stream()
							.filter(statement -> !statement.isAssertion())
							.map(CodeStatement::normalizedText)
							.forEach(statements::add);
				}
			}
			for (TestMethod testMethod : testClass.testMethods()) {
				testMethod.body().statements().stream()
						.takeWhile(statement -> !statement.isAssertion())
						.map(CodeStatement::normalizedText)
						.forEach(statements::add);
			}
			for (HelperMethod helper : testClass.helperMethods()) {
				helper.body().statements().stream()
						.filter(statement -> !statement.isAssertion() && !statement.normalizedText().startsWith("return "))
						.map(CodeStatement::normalizedText)
						.forEach(statements::add);
			}
		}
		return List.copyOf(statements);
	}

	private static String fieldInitialization(Field field, CodeStatement initialization) {
		return field.name() + " = " + initialization.normalizedText() + ";";
	}
}
