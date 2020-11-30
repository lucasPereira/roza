package br.ufsc.ine.leb.roza.ui;

import br.ufsc.ine.leb.roza.ui.window.Window;

public class RozaUi {

	public static void main(String[] args) throws Exception {
		Hub hub = new Hub();
		Manager manager = new Manager();
		Window window = new Window(hub, manager);
		window.show();
	}

}
