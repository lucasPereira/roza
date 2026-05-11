package br.ufsc.ine.leb.roza.core.modern.parsing;

public final class UnsupportedFeatureException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UnsupportedFeatureException(UnsupportedFeatureDiagnostic diagnostic) {
		super(diagnostic.message());
	}
}
