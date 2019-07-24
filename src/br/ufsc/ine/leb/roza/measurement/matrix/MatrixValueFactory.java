package br.ufsc.ine.leb.roza.measurement.matrix;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
