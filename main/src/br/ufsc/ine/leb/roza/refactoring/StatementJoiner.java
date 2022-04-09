package br.ufsc.ine.leb.roza.refactoring;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class StatementJoiner {

	public List<RozaStatement> join(List<List<RozaStatement>> blocks) {
		if (blocks.size() <= 1) {
			return Arrays.asList();
		}
		List<RozaStatement> shared = new LinkedList<RozaStatement>();
		Iterator<List<RozaStatement>> blocksIterator = blocks.iterator();
		if (blocksIterator.hasNext()) {
			shared.addAll(blocksIterator.next());
		}
		while (blocksIterator.hasNext()) {
			List<RozaStatement> join = new LinkedList<RozaStatement>();
			Iterator<RozaStatement> sharedIterator = shared.iterator();
			Iterator<RozaStatement> blockIterator = blocksIterator.next().iterator();
			Boolean stop = false;
			while (!stop && sharedIterator.hasNext() && blockIterator.hasNext()) {
				RozaStatement nextOfShared = sharedIterator.next();
				RozaStatement nextOfBlock = blockIterator.next();
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
