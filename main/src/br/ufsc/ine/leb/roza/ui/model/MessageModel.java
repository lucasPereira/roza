package br.ufsc.ine.leb.roza.ui.model;

import java.awt.Color;

public class MessageModel {

	private String message;
	private Color color;

	public MessageModel(String message, Color color) {
		this.message = message;
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public String getMessage() {
		return message;
	}

}
