package br.ufsc.ine.leb.roza.measurer.matrix;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
