package br.ufsc.ine.leb.roza.ui.window.messages;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.ufsc.ine.leb.roza.ui.Hub;
import br.ufsc.ine.leb.roza.ui.Manager;
import br.ufsc.ine.leb.roza.ui.UiComponent;
import br.ufsc.ine.leb.roza.ui.model.MessageModel;
import br.ufsc.ine.leb.roza.ui.window.Window;

public class Messages implements UiComponent {

	private final Window window;
	private final List<MessageModel> messages;
	private Integer index;
	private JPanel panel;
	private JLabel messageLabel;
	private JButton previous;
	private JButton next;
	private JLabel counterLabel;

	public Messages(Window window) {
		this.window = window;
		this.messages = new LinkedList<>();
		this.index = -1;
	}

	@Override
	public void init(Hub hub, Manager manager) {
		panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		messageLabel = new JLabel("Sem mensagens");
		counterLabel = new JLabel("-");
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
		hub.errorMessageSubscribe(message -> {
			index++;
			Color color = new Color(220, 53, 69, 64);
			messages.add(new MessageModel(message, color));
			displayCurrent();
			updateButtonsState();
		});
		previous.addActionListener(event -> {
			index--;
			displayCurrent();
			updateButtonsState();
		});
		next.addActionListener(event -> {
			index++;
			displayCurrent();
			updateButtonsState();
		});
		counterLabel.setBackground(Color.DARK_GRAY);
		counterLabel.setOpaque(true);
		counterLabel.setBorder(new EmptyBorder(5, 10, 5, 10));
		counterLabel.setForeground(Color.WHITE);
		panel.add(messageLabel);
		panel.add(previous);
		panel.add(next);
		panel.add(counterLabel);
		window.addTopComponent(panel);
	}

	private void updateButtonsState() {
		previous.setEnabled(index > 0);
		next.setEnabled(index < messages.size() - 1);
	}

	private void displayCurrent() {
		MessageModel current = messages.get(index);
		counterLabel.setText(String.format("%d of %d", index + 1 , messages.size()));
		panel.setBackground(current.getColor());
		messageLabel.setText("<html>" + current.getMessage() + "</html>");
	}

	@Override
	public void addChildren(List<UiComponent> children) {}

	@Override
	public void start() {}

}
