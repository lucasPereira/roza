package br.ufsc.ine.leb.roza.extraction;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public interface AssertionDetector {

	Boolean isAssertion(RozaStatement fixture);

}
