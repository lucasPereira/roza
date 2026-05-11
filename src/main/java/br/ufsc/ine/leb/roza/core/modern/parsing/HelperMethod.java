package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.List;
import java.util.Objects;

public final class HelperMethod {

	private final List<String> modifiers;
	private final String returnType;
	private final String name;
	private final List<String> parameters;
	private final List<CodeAnnotation> annotations;
	private final CodeBlock body;

	public HelperMethod(List<String> modifiers, String returnType, String name, List<String> parameters, List<CodeAnnotation> annotations, CodeBlock body) {
		this.modifiers = List.copyOf(Objects.requireNonNull(modifiers));
		this.returnType = Objects.requireNonNull(returnType);
		this.name = Objects.requireNonNull(name);
		this.parameters = List.copyOf(Objects.requireNonNull(parameters));
		this.annotations = List.copyOf(Objects.requireNonNull(annotations));
		this.body = Objects.requireNonNull(body);
	}

	public List<String> modifiers() {
		return modifiers;
	}

	public String returnType() {
		return returnType;
	}

	public String name() {
		return name;
	}

	public List<String> parameters() {
		return parameters;
	}

	public List<CodeAnnotation> annotations() {
		return annotations;
	}

	public CodeBlock body() {
		return body;
	}
}
