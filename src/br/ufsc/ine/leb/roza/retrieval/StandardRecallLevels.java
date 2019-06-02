package br.ufsc.ine.leb.roza.retrieval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StandardRecallLevels implements Iterable<RecallLevel> {

	private List<RecallLevel> levels;

	public StandardRecallLevels() {
		levels = new ArrayList<>(11);
		for (Integer level = 0; level <= 10; level++) {
			levels.add(new RecallLevel(level));
		}
	}

	@Override
	public Iterator<RecallLevel> iterator() {
		return levels.iterator();
	}

}
