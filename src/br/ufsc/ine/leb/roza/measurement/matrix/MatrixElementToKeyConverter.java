package br.ufsc.ine.leb.roza.measurement.matrix;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
