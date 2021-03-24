package br.ufsc.ine.leb.roza.ui.window.messages;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.MessageModel;
import br.ufsc.ine.leb.roza.ui.window.Window;

public class Messages implements UiComponent {

	private Window window;
	private List<MessageModel> messages;
	private Integer index;
	private JPanel panel;
	private JLabel label;
	private JButton previous;
	private JButton next;

	public Messages(Window window) {
		this.window = window;
		this.messages = new LinkedList<>();
		this.index = -1;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		label = new JLabel("Sem mensagens");
		previous = new JButton("<");
		next = new JButton(">");
		previous.setEnabled(false);
		next.setEnabled(false);
		hub.infoMessageSubscribe(message -> {
			index++;
			Color color = new Color(13, 202, 237);
			messages.add(new MessageModel(message, color));
			displayCurrent();
			updateButtonsState();
		});
		previous.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				index--;
				displayCurrent();
				updateButtonsState();
			}

		});
		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				index++;
				displayCurrent();
				updateButtonsState();
			}

		});
		panel.add(label);
		panel.add(previous);
		panel.add(next);
		window.addTopComponent(panel);
	}

	private void updateButtonsState() {
		previous.setEnabled(index > 0 ? true : false);
		next.setEnabled(index < messages.size() - 1 ? true : false);
	}

	private void displayCurrent() {
		MessageModel current = messages.get(index);
		panel.setBackground(current.getColor());
		label.setText(current.getMessage());
	}

	@Override
	public void addChilds(List<UiComponent> childs) {}

	@Override
	public void start() {}

}
