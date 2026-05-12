package br.ufsc.ine.leb.roza.core.modern.analytics;

import br.ufsc.ine.leb.roza.core.modern.decomposition.DecomposedTestCases;
import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RefactoredTestClasses;

public interface TestCodeAnalytics {

	TestCodeAnalyticsReport analyze(ParsedTestClasses originalTestClasses, DecomposedTestCases acceptedTestCases, RefactoredTestClasses refactoredTestClasses);
}
