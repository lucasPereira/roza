package br.ufsc.ine.leb.roza.utils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollectionUtils {

	public <T> Set<T> set() {
		return new HashSet<>();
	}

	public <T> Set<T> set(T element) {
		return new HashSet<>(Collections.singletonList(element));
	}

	public <T> Set<T> set(T first, T second) {
		return new HashSet<>(List.of(first, second));
	}

	public <T> Set<T> set(T first, T second, T third) {
		return new HashSet<>(List.of(first, second, third));
	}

}
