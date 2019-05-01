package br.ufsc.ine.leb.roza.collections;

public interface MatrixElementToKeyConverter<T, K> {

	K convert(T element);

}
