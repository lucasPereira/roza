package br.ufsc.ine.leb.roza.core.measurement.matrix.deckard;

import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixElementToKeyConverter;

public class DeckardMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
