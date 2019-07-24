package br.ufsc.ine.leb.roza.measurement.matrix;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import br.ufsc.ine.leb.roza.exceptions.MissingPairException;

public class Matrix<T, K, V> {

	private Map<K, T> keys;
	private Map<T, Map<T, V>> matrix;

	public Matrix(List<T> elements, MatrixElementToKeyConverter<T, K> converter, MatrixValueFactory<T, V> factory) {
		matrix = new HashMap<>();
		keys = new HashMap<>();
		for (T source : elements) {
			keys.put(converter.convert(source), source);
			for (T target : elements) {
				if (!matrix.containsKey(source)) {
					matrix.put(source, new HashMap<>());
				}
				V value = factory.create(source, target);
				matrix.get(source).put(target, value);
			}
		}
	}

	public List<MatrixPair<T, V>> getPairs() {
		List<MatrixPair<T, V>> pairs = new LinkedList<>();
		for (Map.Entry<T, Map<T, V>> entrySource : matrix.entrySet()) {
			for (Map.Entry<T, V> entryTarget : entrySource.getValue().entrySet()) {
				MatrixPair<T, V> pair = new MatrixPair<>(entrySource.getKey(), entryTarget.getKey(), entryTarget.getValue());
				pairs.add(pair);
			}
		}
		return pairs;
	}

	public V get(K sourceKey, K targetKey) {
		T source = keys.get(sourceKey);
		T target = keys.get(targetKey);
		if (!matrix.containsKey(source) || !matrix.get(source).containsKey(target)) {
			throw new MissingPairException(sourceKey.toString(), targetKey.toString());
		}
		return matrix.get(source).get(target);
	}

	public void set(K sourceKey, K targetKey, V value) {
		T source = keys.get(sourceKey);
		T target = keys.get(targetKey);
		if (!matrix.containsKey(source) || !matrix.get(source).containsKey(target)) {
			throw new MissingPairException(sourceKey.toString(), targetKey.toString());
		}
		matrix.get(source).put(target, value);
	}

}
