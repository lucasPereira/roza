package br.ufsc.ine.leb.roza.support.matrix;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
