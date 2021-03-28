package br.ufsc.ine.leb.roza.ui.window.content.distribution;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.shared.WrapLayout;
import br.ufsc.ine.leb.roza.utils.comparator.TestCaseComparatorByName;

public class ClustersPanel implements UiComponent {

	private DistributionTab distributionTab;

	public ClustersPanel(DistributionTab distributionTab) {
		this.distributionTab = distributionTab;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JPanel panel = new JPanel();
		panel.setLayout(new WrapLayout(WrapLayout.LEFT));
		JScrollPane scroll = new JScrollPane(panel);
		distributionTab.addComponent("Clusters", scroll);
		hub.extractTestCasesSubscribe(testCases -> {
			Collections.sort(testCases, new TestCaseComparatorByName());
			for (TestCase test : testCases) {
				JPanel clusterPanel = new JPanel();
				clusterPanel.setBorder(BorderFactory.createLineBorder(Color.black));
				JLabel testLabel = new JLabel(test.getName());
				clusterPanel.add(testLabel);
				panel.add(clusterPanel);
			}
		});
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
