package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Objects;
import java.util.Optional;

public final class SetupAnnotation {

	private final CodeAnnotation annotation;
	private final Optional<String> importDeclaration;

	public SetupAnnotation(CodeAnnotation annotation, Optional<String> importDeclaration) {
		this.annotation = Objects.requireNonNull(annotation);
		this.importDeclaration = Objects.requireNonNull(importDeclaration);
	}

	public CodeAnnotation annotation() {
		return annotation;
	}

	public Optional<String> importDeclaration() {
		return importDeclaration;
	}

	public boolean isBeforeEach() {
		return "BeforeEach".equals(annotation.name());
	}
}
