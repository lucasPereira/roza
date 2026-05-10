package br.ufsc.ine.leb.roza.core.legacy.measurement.matrix.deckard;

import br.ufsc.ine.leb.roza.core.legacy.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.legacy.measurement.matrix.MatrixElementToKeyConverter;

public class DeckardMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getFileName();
	}

}
