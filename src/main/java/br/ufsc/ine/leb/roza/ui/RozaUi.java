package br.ufsc.ine.leb.roza.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import br.ufsc.ine.leb.roza.ui.window.Window;
import br.ufsc.ine.leb.roza.utils.RozaLogger;

public class RozaUi {

	public static void main(String[] args) {
		Hub hub = new Hub();
		Manager manager = new Manager();
		Window window = new Window();
		Queue<UiComponent> init = new LinkedList<>();
		List<UiComponent> start = new LinkedList<>();
		init.add(window);
		start.add(window);
		while (!init.isEmpty()) {
			UiComponent component = init.remove();
			runComponentPhase("init", () -> component.init(hub, manager));
			List<UiComponent> children = new LinkedList<>();
			runComponentPhase("addChildren", () -> component.addChildren(children));
			init.addAll(children);
			start.addAll(children);
		}
		for (UiComponent component : start) {
			runComponentPhase("start", component::start);
		}
	}

	private static void runComponentPhase(String name, Runnable phase) {
		try {
			phase.run();
		} catch (Exception exception) {
			RozaLogger.getInstance().error(String.format("Failed to %s in component", name), exception);
		}
	}
}
