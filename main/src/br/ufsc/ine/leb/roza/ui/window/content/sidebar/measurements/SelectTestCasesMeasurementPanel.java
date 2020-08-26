package br.ufsc.ine.leb.roza.ui.window.content.sidebar.measurements;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestCase;
import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.TestCaseRenderer;
import br.ufsc.ine.leb.roza.utils.FormatterUtils;
import br.ufsc.ine.leb.roza.utils.ReportUtils;

public class SelectTestCasesMeasurementPanel implements UiComponent {

	private static final String DEFAULT_SCORE = "---------";

	private Hub hub;
	private MeasurementsTab measurementsTab;
	private SimilarityReport similarityReport;

	public SelectTestCasesMeasurementPanel(Hub hub, MeasurementsTab measurementsTab) {
		this.hub = hub;
		this.measurementsTab = measurementsTab;
		init();
		createChilds();
	}

	@Override
	public void init() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));
		JComboBox<TestCase> sourceCombo = new JComboBox<>();
		JComboBox<TestCase> targetCombo = new JComboBox<>();
		JLabel scoreLabel = new JLabel("", SwingConstants.CENTER);
		scoreLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 16));
		scoreLabel.setText(DEFAULT_SCORE);
		scoreLabel.setOpaque(true);
		DefaultComboBoxModel<TestCase> sourceModel = new DefaultComboBoxModel<>();
		DefaultComboBoxModel<TestCase> targetModel = new DefaultComboBoxModel<>();
		sourceCombo.setModel(sourceModel);
		targetCombo.setModel(targetModel);
		sourceCombo.setRenderer(new TestCaseRenderer());
		targetCombo.setRenderer(new TestCaseRenderer());
		hub.loadTestClassesSubscribe(testClasses -> {
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
		});
		hub.extractTestCasesSubscribe(testCases -> {
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
		});
		hub.measureTestsSubscribe(similarityReport -> {
			this.similarityReport = similarityReport;
			List<TestCase> testCases = new ReportUtils().getUniqueTestCases(similarityReport);
			sourceModel.removeAllElements();
			targetModel.removeAllElements();
			testCases.forEach(testCase -> {
				sourceModel.addElement(testCase);
				targetModel.addElement(testCase);
			});
			sourceCombo.setMaximumSize(sourceCombo.getPreferredSize());
			targetCombo.setMaximumSize(targetCombo.getPreferredSize());
		});
		sourceCombo.addActionListener(createListenerToTestSelection(scoreLabel, sourceCombo, targetCombo));
		targetCombo.addActionListener(createListenerToTestSelection(scoreLabel, sourceCombo, targetCombo));
		panel.add(new JScrollPane(sourceCombo));
		panel.add(new JScrollPane(targetCombo));
		panel.add(scoreLabel);
		updateScore(scoreLabel, sourceCombo, targetCombo);
		measurementsTab.addComponent(panel);
	}

	private ActionListener createListenerToTestSelection(JLabel scoreLabel, JComboBox<TestCase> sourceCombo, JComboBox<TestCase> targetCombo) {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				updateScore(scoreLabel, sourceCombo, targetCombo);
			}

		};
	}

	private void updateScore(JLabel scoreLabel, JComboBox<TestCase> sourceCombo, JComboBox<TestCase> targetCombo) {
		TestCase source = (TestCase) sourceCombo.getSelectedItem();
		TestCase target = (TestCase) targetCombo.getSelectedItem();
		if (source != null && target != null) {
			SimilarityAssessment measure = similarityReport.getPair(source, target);
			String score = new FormatterUtils().bigDecimal(measure.getScore());
			scoreLabel.setText(score);
		} else {
			scoreLabel.setText(DEFAULT_SCORE);
		}
	}

	@Override
	public void createChilds() {}

}
