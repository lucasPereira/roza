package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;
import java.util.Optional;

public final class TestCodeViolation {

	private final ViolationScope scope;
	private final String testClassName;
	private final Optional<String> testMethodName;
	private final String description;
	private final String codeSnippet;

	public TestCodeViolation(ViolationScope scope, String testClassName, String description) {
		this(scope, testClassName, Optional.empty(), description, "");
	}

	public TestCodeViolation(ViolationScope scope, String testClassName, String testMethodName, String description) {
		this(scope, testClassName, Optional.of(Objects.requireNonNull(testMethodName)), description, "");
	}

	public TestCodeViolation(ViolationScope scope, String testClassName, String testMethodName, String description, String codeSnippet) {
		this(scope, testClassName, Optional.of(Objects.requireNonNull(testMethodName)), description, codeSnippet);
	}

	public TestCodeViolation(ViolationScope scope, String testClassName, Optional<String> testMethodName, String description, String codeSnippet) {
		this.scope = Objects.requireNonNull(scope);
		this.testClassName = Objects.requireNonNull(testClassName);
		this.testMethodName = Objects.requireNonNull(testMethodName);
		this.description = Objects.requireNonNull(description);
		this.codeSnippet = Objects.requireNonNull(codeSnippet);
	}

	public ViolationScope scope() {
		return scope;
	}

	public String testClassName() {
		return testClassName;
	}

	public Optional<String> testMethodName() {
		return testMethodName;
	}

	public String description() {
		return description;
	}

	public String codeSnippet() {
		return codeSnippet;
	}
}
