package br.ufsc.ine.leb.roza.measurer.matrix.deckard;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixElementToKeyConverter;

public class DeckardMatrixElementToKeyConverter implements MatrixElementToKeyConverter<TestCaseMaterialization, String> {

	@Override
	public String convert(TestCaseMaterialization element) {
		return element.getAbsoluteFilePath();
	}

}
