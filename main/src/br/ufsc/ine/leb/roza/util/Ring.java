package br.ufsc.ine.leb.roza.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.runners.Suite.SuiteClasses;

import br.ufsc.ine.leb.roza.ui.window.content.sidebar.clustering.ClusteringTab;

@SuiteClasses({ ClusteringTab.class })
public class Ring<T> {

	private List<T> elements;
	private Integer index;

	public Ring() {
		elements = new ArrayList<>();
		index = 0;
	}

	public void shuffe() {
		Collections.shuffle(elements);
	}

	public void add(T element) {
		elements.add(element);
	}

	public T next() {
		if (elements.isEmpty()) {
			throw new NoSuchElementException();
		}
		Integer current = index % elements.size();
		index++;
		return elements.get(current);
	}

	public void reset() {
		index = 0;
	}

}
