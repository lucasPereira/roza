package br.ufsc.ine.leb.roza.ui;

import br.ufsc.ine.leb.roza.ui.window.Window;

public class Main {

	public static void main(String[] args) throws Exception {
		Hub hub = new Hub();
		Window window = new Window(hub);
		window.show();
	}

}
