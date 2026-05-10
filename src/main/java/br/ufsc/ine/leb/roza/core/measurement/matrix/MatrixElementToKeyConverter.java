package br.ufsc.ine.leb.roza.core.measurement.matrix;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
