package br.ufsc.ine.leb.roza.exceptions;

public class MissingPairException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingPairException(String source, String target) {
		super(String.format("Missing pair: %s -> %s", source, target));
	}

}
