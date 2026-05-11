package br.ufsc.ine.leb.roza.core.modern.parsing;

public final class ParsingException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String codeFileSource;

	public ParsingException(String codeFileSource, String message, Throwable cause) {
		super(withSource(codeFileSource, message), cause);
		this.codeFileSource = codeFileSource != null ? codeFileSource : "";
	}

	/** Path or label of the file being parsed when this failure occurred; may be blank. */
	public String codeFileSource() {
		return codeFileSource;
	}

	private static String withSource(String codeFileSource, String message) {
		if (codeFileSource == null || codeFileSource.isBlank()) {
			return message;
		}
		return codeFileSource + ": " + message;
	}
}
