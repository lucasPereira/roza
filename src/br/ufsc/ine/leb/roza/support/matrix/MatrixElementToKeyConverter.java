package br.ufsc.ine.leb.roza.support.matrix;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
