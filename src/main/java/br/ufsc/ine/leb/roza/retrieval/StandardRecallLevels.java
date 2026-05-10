package br.ufsc.ine.leb.roza.retrieval;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class StandardRecallLevels implements Iterable<RecallLevel> {

	private final List<RecallLevel> levels;

	public StandardRecallLevels() {
		levels = new ArrayList<>(11);
		for (int level = 0; level <= 10; level++) {
			levels.add(new RecallLevel(level));
		}
	}

	@Override
	@NotNull
	public Iterator<RecallLevel> iterator() {
		return levels.iterator();
	}

}
