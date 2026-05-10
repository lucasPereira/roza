package br.ufsc.ine.leb.roza.core.legacy.measurement.matrix;

public interface MatrixValueFactory<K, V> {

	V create(K source, K target);

}
