package br.ufsc.ine.leb.roza.core.modern.decomposition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.modern.parsing.TestClass;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestCodeViolation;
import br.ufsc.ine.leb.roza.core.modern.parsing.TestMethod;
import br.ufsc.ine.leb.roza.core.modern.parsing.ViolationScope;

public final class TestCodeEligibility {

	private final Set<String> excludedClasses;
	private final Set<String> excludedMethods;

	public TestCodeEligibility(List<TestCodeViolation> violations) {
		excludedClasses = new HashSet<>();
		excludedMethods = new HashSet<>();
		for (TestCodeViolation violation : violations) {
			if (violation.scope() == ViolationScope.TEST_CLASS) {
				excludedClasses.add(violation.testClassName());
			} else {
				violation.testMethodName()
						.map(methodName -> methodKey(violation.testClassName(), methodName))
						.ifPresent(excludedMethods::add);
			}
		}
	}

	public boolean accepts(TestClass testClass) {
		return !excludedClasses.contains(testClass.qualifiedName());
	}

	public boolean accepts(TestClass testClass, TestMethod testMethod) {
		return accepts(testClass) && !excludedMethods.contains(methodKey(testClass.qualifiedName(), testMethod.name()));
	}

	private String methodKey(String testClassName, String testMethodName) {
		return testClassName + "#" + testMethodName;
	}
}
