package br.ufsc.ine.leb.roza.ui.window.content.graph;

import java.awt.Component;
import java.util.List;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.window.content.Content;

public class GraphCanvas implements UiComponent {

	private Content content;

	public GraphCanvas(Content content) {
		this.content = content;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Graph graph = new SingleGraph("roza");
		graph.addNode("A");
		graph.addNode("B");
		graph.addNode("C");
		graph.addEdge("AB", "A", "B");
		graph.addEdge("BC", "B", "C");
		graph.addEdge("CA", "C", "A");
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		Component view = viewer.addDefaultView(false);
		viewer.enableAutoLayout();
		content.addLeftComponent(view);
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
