package br.ufsc.ine.leb.roza.measurer.matrix;

public class MatrixPair<K, V> {

	private K source;
	private K target;
	private V value;

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
