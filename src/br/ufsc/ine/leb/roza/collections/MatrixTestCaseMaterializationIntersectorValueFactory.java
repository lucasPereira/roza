package br.ufsc.ine.leb.roza.collections;

import br.ufsc.ine.leb.roza.TestCaseMaterialization;

public class MatrixTestCaseMaterializationIntersectorValueFactory implements MatrixValueFactory<TestCaseMaterialization, Intersector> {

	@Override
	public Intersector create(TestCaseMaterialization source, TestCaseMaterialization target) {
		Intersector intersector = new Intersector(source.getLength());
		if (source.equals(target)) {
			intersector.addSegment(1, source.getLength());
		}
		return intersector;
	}

}
