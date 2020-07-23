package br.ufsc.ine.leb.roza.ui.window.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JComboBox;

import br.ufsc.ine.leb.roza.extraction.Junit4TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.Junit5TestCaseExtractor;
import br.ufsc.ine.leb.roza.extraction.JunitTestCaseExtractor;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;

public class ExtractorComboBox implements UiComponent {

	private Hub hub;
	private Manager manager;
	private Toolbar toolbar;

	public ExtractorComboBox(Hub hub, Manager manager, Toolbar toolbar) {
		this.hub = hub;
		this.manager = manager;
		this.toolbar = toolbar;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JComboBox<String> combo = new JComboBox<String>();
		combo.setToolTipText("Extractor");
		combo.addItem("JUnit 4");
		combo.addItem("JUnit 4 - OMP/CAPES Project");
		combo.addItem("JUnit 5");
		combo.setMaximumSize(combo.getPreferredSize());
		combo.setEnabled(false);
		hub.loadTestClassesSubscribe(classes -> combo.setEnabled(true));
		combo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				Integer selected = combo.getSelectedIndex();
				switch (selected) {
					case 0:
						manager.setTestCaseExtractor(new Junit4TestCaseExtractor());
						break;
					case 1:
						List<String> assertions = Arrays.asList("assegurarTexto", "assegurarValor", "assegurarQuantidadeDeElementos", "assegurarConteudoDeArquivoBaixado", "assegurarNaoMarcado", "assegurarMarcado", "assegurarMarcacao");
						manager.setTestCaseExtractor(new JunitTestCaseExtractor(assertions));
						break;
					case 2:
						manager.setTestCaseExtractor(new Junit5TestCaseExtractor());
						break;
					default:
						break;
				}
			}

		});
		toolbar.addComponent(combo);
	}

	@Override
	public void createChilds() {}

}
