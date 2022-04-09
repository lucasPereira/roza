package br.ufsc.ine.leb.roza.exceptions;

import br.ufsc.ine.leb.roza.extraction.TestCase;

public class MissingPairException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MissingPairException(String source, String target) {
		super(String.format("Missing pair: %s -> %s", source, target));
	}

	public MissingPairException(TestCase source, TestCase target) {
		super(String.format("Missing pair: %s -> %s", source, target));
	}

}
