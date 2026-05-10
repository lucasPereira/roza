package br.ufsc.ine.leb.roza.refactoring;

import br.ufsc.ine.leb.roza.Statement;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class StatementJoiner {

	public List<Statement> join(List<List<Statement>> blocks) {
		if (blocks.size() <= 1) {
			return List.of();
		}
		List<Statement> shared = new LinkedList<>();
		Iterator<List<Statement>> blocksIterator = blocks.iterator();
		if (blocksIterator.hasNext()) {
			shared.addAll(blocksIterator.next());
		}
		while (blocksIterator.hasNext()) {
			List<Statement> join = new LinkedList<>();
			Iterator<Statement> sharedIterator = shared.iterator();
			Iterator<Statement> blockIterator = blocksIterator.next().iterator();
			boolean stop = false;
			while (!stop && sharedIterator.hasNext() && blockIterator.hasNext()) {
				Statement nextOfShared = sharedIterator.next();
				Statement nextOfBlock = blockIterator.next();
				if (nextOfShared.equals(nextOfBlock)) {
					join.add(nextOfShared);
				} else {
					stop = true;
				}
			}
			shared = join;
		}
		return shared;
	}

}
