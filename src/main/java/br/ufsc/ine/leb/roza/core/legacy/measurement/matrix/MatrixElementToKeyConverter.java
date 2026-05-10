package br.ufsc.ine.leb.roza.core.legacy.measurement.matrix;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
