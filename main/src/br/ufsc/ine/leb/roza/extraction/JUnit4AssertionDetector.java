package br.ufsc.ine.leb.roza.extraction;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class JUnit4AssertionDetector implements AssertionDetector {

	@Override
	public Boolean isAssertion(RozaStatement statement) {
		return statement.toCode().startsWith("assertEquals");
	}

}
