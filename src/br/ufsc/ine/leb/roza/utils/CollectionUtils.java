package br.ufsc.ine.leb.roza.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CollectionUtils {

	public <T> Set<T> set() {
		return new HashSet<>();
	}

	public <T> Set<T> set(T element) {
		return new HashSet<>(Arrays.asList(element));
	}

	public <T> Set<T> set(T first, T second) {
		return new HashSet<>(Arrays.asList(first, second));
	}

	public <T> Set<T> set(T first, T second, T third) {
		return new HashSet<>(Arrays.asList(first, second, third));
	}

}
