package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import br.ufsc.ine.leb.roza.TestClass;

public class Hub {

	private List<Consumer<List<TestClass>>> loadClassesListeners;

	public Hub() {
		loadClassesListeners = new LinkedList<>();
	}

	public void loadClassesPublish(List<TestClass> classes) {
		for (Consumer<List<TestClass>> listener : loadClassesListeners) {
			listener.accept(classes);
		}
	}

	public void loadClassesSubscribe(Consumer<List<TestClass>> listener) {
		loadClassesListeners.add(listener);
	}

}
