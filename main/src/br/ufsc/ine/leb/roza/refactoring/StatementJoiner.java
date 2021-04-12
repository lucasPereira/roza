package br.ufsc.ine.leb.roza.refactoring;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.Statement;

public class StatementJoiner {

	public List<Statement> join(List<List<Statement>> blocks) {
		if (blocks.size() <= 1) {
			return Arrays.asList();
		}
		List<Statement> shared = new LinkedList<Statement>();
		Iterator<List<Statement>> blocksIterator = blocks.iterator();
		if (blocksIterator.hasNext()) {
			shared.addAll(blocksIterator.next());
		}
		while (blocksIterator.hasNext()) {
			List<Statement> join = new LinkedList<Statement>();
			Iterator<Statement> sharedIterator = shared.iterator();
			Iterator<Statement> blockIterator = blocksIterator.next().iterator();
			Boolean stop = false;
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

//	private List<Statement> findSharedFixtures(List<TestCase> testCases) {
		
//		while (testCaseIterator.hasNext()) {
//			Iterator<Statement> fixtureIterator = testCaseIterator.next().getFixtures().iterator();
//			Iterator<Statement> sharedFixtureIterator = sharedFixtures.iterator();
//			List<Statement> joinFixtures = new LinkedList<Statement>();
//			Boolean stop = false;
//			while (!stop && fixtureIterator.hasNext() && sharedFixtureIterator.hasNext()) {
//				Statement fixture = fixtureIterator.next();
//				Statement sharedFixture = sharedFixtureIterator.next();
//				if (fixture.equals(sharedFixture)) {
//					joinFixtures.add(fixture);
//					stop = true;
//				}
//			}
//			sharedFixtures = joinFixtures;
//		}
//		return sharedFixtures;
//	}

}
