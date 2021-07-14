package br.ufsc.ine.leb.roza.ui.window.content.distribution;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.clustering.Level;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.WrapLayout;
import br.ufsc.ine.leb.roza.utils.Ring;
import br.ufsc.ine.leb.roza.utils.comparator.ClusterComparatorBySizeAndTestName;
import br.ufsc.ine.leb.roza.utils.comparator.TestCaseComparatorByName;

public class ClustersPanel implements UiComponent {

	private DistributionTab distributionTab;
	private JPanel container;
	private Ring<Color> colors;

	public ClustersPanel(DistributionTab distributionTab) {
		this.distributionTab = distributionTab;
		colors = new Ring<>();
		colors.add(new Color(192, 57, 43));
		colors.add(new Color(238, 163, 3));
		colors.add(new Color(241, 196, 15));
		colors.add(new Color(100, 187, 93));
		colors.add(new Color(22, 160, 133));
		colors.add(new Color(14, 131, 205));
		colors.add(new Color(122, 47, 168));
	}

	@Override
	public void init(Hub hub, Manager manager) {
		container = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JScrollPane scroll = new JScrollPane(container);
		distributionTab.addComponent("Clusters", scroll);
		hub.loadTestClassesSubscribe(classes -> {
			clear();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			clear();
		});
		hub.measureTestsSubscribe(similarityReport -> {
			clear();
		});
		hub.distributeTestsSubscribe(levels -> {
			clear();
			colors.shuffe();
			showLevel(levels.get(0));
		});
		hub.selectLevelSubscribe(level -> {
			clear();
			colors.reset();
			showLevel(level);
		});
	}

	private void clear() {
		container.removeAll();
	}

	private void showLevel(Level level) {
		JPanel panel = new JPanel();
		panel.setLayout(new WrapLayout(WrapLayout.LEFT));
		List<Cluster> clusters = new ArrayList<>(level.getClusters());
		Collections.sort(clusters, new ClusterComparatorBySizeAndTestName());
		Collections.reverse(clusters);
		for (Cluster cluster : clusters) {
			List<TestCase> tests = new ArrayList<>(cluster.getTestCases());
			Collections.reverse(tests);
			Collections.sort(tests, new TestCaseComparatorByName());
			JPanel clusterPanel = new JPanel();
			Color color = (tests.size() > 1) ? colors.next() : Color.DARK_GRAY;
			clusterPanel.setBackground(color);
			String lists = "";
			for (TestCase test : tests) {
				lists += "<ul>" + test.getName() + "</ul>";
			}
			JLabel label = new JLabel("<html>" + lists + "</html>");
			label.setForeground(Color.WHITE);
			label.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
			clusterPanel.add(label);
			panel.add(clusterPanel);
		}
		container.add(panel);
		container.validate();
		container.repaint();
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
