package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.ufsc.ine.leb.roza.ui.window.Window;

public class RozaUi {

	public static void main(String[] args) throws Exception {
		Hub hub = new Hub();
		Manager manager = new Manager();
		Window window = new Window();
		Queue<UiComponent> init = new LinkedList<>();
		List<UiComponent> start = new LinkedList<>();
		init.add(window);
		start.add(window);
		while (!init.isEmpty()) {
			List<UiComponent> childs = new LinkedList<>();
			UiComponent component = init.remove();
			runComponentPhase("init", () -> component.init(hub, manager));
			runComponentPhase("addChilds", () -> component.addChilds(childs));
			init.addAll(childs);
			start.addAll(childs);
		}
		for (UiComponent component : start) {
			runComponentPhase("start", () -> component.start());
		}
	}

	private static void runComponentPhase(String name, Runnable phase) {
		try {
			phase.run();
		} catch (Exception exception) {
			new RuntimeException(String.format("Failed to %s in component", name), exception).printStackTrace();
		}
	}
}
