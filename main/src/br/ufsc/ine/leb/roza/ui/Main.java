package br.ufsc.ine.leb.roza.ui;

public class Main {

	public static void main(String[] args) throws Exception {
		Window window = new Window();

		Toolbar toolbar = new Toolbar(window);
		new LoaderButton(toolbar);

		Content content = new Content(window);
		new GraphCanvas(content);
		new Sidebar(content);

		window.show();
	}

}
