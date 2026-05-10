package br.ufsc.ine.leb.roza.core.measurement.matrix.simian;

import br.ufsc.ine.leb.roza.core.TestCaseMaterialization;
import br.ufsc.ine.leb.roza.core.measurement.intersector.Intersector;
import br.ufsc.ine.leb.roza.core.measurement.matrix.MatrixValueFactory;

public class SimianMatrixValueFactory implements MatrixValueFactory<TestCaseMaterialization, Intersector> {

	@Override
	public Intersector create(TestCaseMaterialization source, TestCaseMaterialization target) {
		Intersector intersector = new Intersector(source.getLength());
		if (source.equals(target)) {
			intersector.addSegment(1, source.getLength());
		}
		return intersector;
	}

}
