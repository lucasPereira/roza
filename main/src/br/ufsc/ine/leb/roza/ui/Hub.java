package br.ufsc.ine.leb.roza.ui;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Hub {

	private List<Consumer<List<File>>> classesLoadedListeners;

	public Hub() {
		classesLoadedListeners = new LinkedList<>();
	}

	public void classesLoadedPublish(List<File> classes) {
		for (Consumer<List<File>> listener : classesLoadedListeners) {
			listener.accept(classes);
		}
	}

	public void classesLoadedSubscribe(Consumer<List<File>> listener) {
		classesLoadedListeners.add(listener);
	}

}
