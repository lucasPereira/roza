package br.ufsc.ine.leb.roza.measurer.matrix;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
