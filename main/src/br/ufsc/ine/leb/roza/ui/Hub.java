package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Hub {

	private List<Consumer<List<File>>> loadClassesListeners;

	public Hub() {
		loadClassesListeners = new LinkedList<>();
	}

	public void loadClassesPublish(List<File> classes) {
		for (Consumer<List<File>> listener : loadClassesListeners) {
			listener.accept(classes);
		}
	}

	public void loadClassesSubscribe(Consumer<List<File>> listener) {
		loadClassesListeners.add(listener);
	}

}
