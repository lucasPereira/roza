package br.ufsc.ine.leb.roza.measurer.matrix.deckard;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.measurer.intersector.Intersector;
import br.ufsc.ine.leb.roza.measurer.matrix.MatrixValueFactory;

public class DeckardMatrixValueFactory implements MatrixValueFactory<TestCaseMaterialization, Intersector> {

	@Override
	public Intersector create(TestCaseMaterialization source, TestCaseMaterialization target) {
		Intersector intersector = new Intersector(source.getLength());
		if (source.equals(target)) {
			intersector.addSegment(1, source.getLength());
		}
		return intersector;
	}

}