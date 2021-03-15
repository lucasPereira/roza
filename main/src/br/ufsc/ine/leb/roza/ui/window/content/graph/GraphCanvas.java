package br.ufsc.ine.leb.roza.ui.window.content.graph;

import java.awt.Component;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
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
		hub.loadTestClassesSubscribe(classes -> graph.clear());
		hub.extractTestCasesSubscribe(testCases -> graph.clear());
		hub.measureTestsSubscribe(similarityReport -> graph.clear());
		hub.updateClustersSubscribe((Set<Cluster> clusters) -> update(graph, clusters));
		Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		Component view = viewer.addDefaultView(false);
		viewer.enableAutoLayout();
		content.addLeftComponent(view);
	}

	private void update(Graph graph, Set<Cluster> clusters) {
		for (Cluster cluster : clusters) {
			for (TestCase source : cluster.getTestCases()) {
				for (TestCase target : cluster.getTestCases()) {
					addOrCreate(graph, source);
					addOrCreate(graph, target);
				}
			}
		}
	}

	private Node addOrCreate(Graph graph, TestCase test) {
		Node node = graph.getNode(test.getName());
		if (node == null) {
			node = graph.addNode(test.getName());
			node.addAttribute("ui.label", test.getName());
		}
		return node;
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
