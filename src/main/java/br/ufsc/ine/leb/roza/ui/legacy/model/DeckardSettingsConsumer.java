package br.ufsc.ine.leb.roza.ui.legacy.model;

@FunctionalInterface
public interface DeckardSettingsConsumer {

	void accept(Integer minTokens, Integer stride, Double similarity);

}
