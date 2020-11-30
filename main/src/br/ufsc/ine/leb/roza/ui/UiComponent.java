package br.ufsc.ine.leb.roza.ui;

import java.util.List;

public interface UiComponent {

	void init(Hub hub, Manager manager);

	void addChilds(List<UiComponent> childs);

	void start();

}
