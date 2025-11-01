package br.ufsc.ine.leb.roza.measurement.matrix;

public class MatrixPair<K, V> {

	private final K source;
	private final K target;
	private final V value;

	public MatrixPair(K source, K target, V value) {
		this.source = source;
		this.target = target;
		this.value = value;
	}

	public K getSource() {
		return source;
	}

	public K getTarget() {
		return target;
	}

	public V getValue() {
		return value;
	}

}
