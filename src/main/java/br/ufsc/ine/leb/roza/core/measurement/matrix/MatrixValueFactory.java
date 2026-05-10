package br.ufsc.ine.leb.roza.core.measurement.matrix;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
