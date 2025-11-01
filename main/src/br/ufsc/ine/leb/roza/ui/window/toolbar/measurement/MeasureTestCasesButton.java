package br.ufsc.ine.leb.roza.ui.window.toolbar.measurement;

import java.util.List;

import javax.swing.JButton;

import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class MeasureTestCasesButton implements UiComponent {

	private final MeasurementTab toolbar;

	public MeasureTestCasesButton(MeasurementTab toolbar) {
		this.toolbar = toolbar;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		JButton button = new JButton("Measure Tests");
		toolbar.addComponent(button);
		button.addActionListener(listner -> {
			SimilarityReport similarityReort = manager.measureTestCases();
			hub.measureTestsPublish(similarityReort);
			hub.infoMessagePublish("Similarity matrix created");
		});
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
