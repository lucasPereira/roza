package br.ufsc.ine.leb.roza.ui.window.content.graph;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.swing_viewer.DefaultView;
import org.graphstream.ui.swing_viewer.SwingViewer;
import org.graphstream.ui.view.Viewer;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.dendrogram.Level;
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
		hub.startTestsDistributionSubscribe(levels -> update(graph, levels));
		hub.resetTestsDistributionSubscribe(() -> graph.clear());
		SwingViewer viewer = new SwingViewer(graph, Viewer.ThreadingModel.GRAPH_IN_GUI_THREAD);
		DefaultView view = (DefaultView) viewer.addDefaultView(false);
		viewer.enableAutoLayout();
		addMouveListeners(view);
		content.addLeftComponent(view);
	}

	private void addGraphStyle(Graph graph) {
		graph.setAttribute("ui.stylesheet", "graph { fill-color: #00003C, #000010; fill-mode: gradient-vertical; }");
		graph.setAttribute("ui.stylesheet", "node { fill-color: #FFF; }");
		graph.setAttribute("ui.stylesheet", "node { size: 10px; }");
		graph.setAttribute("ui.stylesheet", "node { text-color: #FFF; }");
		graph.setAttribute("ui.stylesheet", "node { text-alignment: under; }");
		graph.setAttribute("ui.stylesheet", "node { text-size: 15px; }");
		graph.setAttribute("ui.quality");
		graph.setAttribute("ui.antialias");
	}

	private void addMouveListeners(DefaultView view) {
		view.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent event) {
				zoom(view, event);
			}

			private void zoom(DefaultView view, MouseWheelEvent event) {
				Integer amount = event.getWheelRotation();
				Double currentZoom = view.getCamera().getViewPercent();
				Double newZoom = amount * 0.1 + currentZoom;
				if (newZoom >= 0.1 && newZoom <= 2) {
					view.getCamera().setViewPercent(newZoom);
				}
			}

			@SuppressWarnings("unused")
			public void centralize(DefaultView view, MouseWheelEvent event) {
				Integer x = event.getX();
				Integer y = event.getY();
				Point3 center = view.getCamera().transformPxToGu(x, y);
				view.getCamera().setViewCenter(center.x, center.y, center.z);
			}

		});
	}

	private void update(Graph graph, List<Level> levels) {
		graph.clear();
		Set<Cluster> clusters = levels.get(0).getClusters();
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
			node.setAttribute("ui.label", test.getName());
		}
		addGraphStyle(graph);
		return node;
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
