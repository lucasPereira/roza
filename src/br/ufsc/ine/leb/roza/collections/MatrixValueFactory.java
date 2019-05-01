package br.ufsc.ine.leb.roza.collections;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
