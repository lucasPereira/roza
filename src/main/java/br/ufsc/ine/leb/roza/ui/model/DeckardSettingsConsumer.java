package br.ufsc.ine.leb.roza.ui.model;

@FunctionalInterface
public interface DeckardSettingsConsumer {

	void accept(Integer minTokens, Integer stride, Double similarity);

}
