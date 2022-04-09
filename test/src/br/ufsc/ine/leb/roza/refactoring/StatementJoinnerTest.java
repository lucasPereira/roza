package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.parsing.RozaStatement;

public class StatementJoinnerTest {

	private StatementJoiner joiner;

	@BeforeEach
	void setup() {
		joiner = new StatementJoiner();
	}

	@Test
	void empty() throws Exception {
		List<List<RozaStatement>> blocks = Arrays.asList();
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneEmptyBlock() throws Exception {
		List<RozaStatement> block = Arrays.asList();
		List<List<RozaStatement>> blocks = Arrays.asList(block);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithOneStatement() throws Exception {
		RozaStatement statement = new RozaStatement("System.out.println(0);");
		List<RozaStatement> block = Arrays.asList(statement);
		List<List<RozaStatement>> blocks = Arrays.asList(block);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithTwoStatements() throws Exception {
		RozaStatement statement1 = new RozaStatement("System.out.println(0);");
		RozaStatement statement2 = new RozaStatement("System.out.println(1);");
		List<RozaStatement> block = Arrays.asList(statement1, statement2);
		List<List<RozaStatement>> blocks = Arrays.asList(block);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithDistinctStatements() throws Exception {
		RozaStatement statement1 = new RozaStatement("System.out.println(0);");
		RozaStatement statement2 = new RozaStatement("System.out.println(1);");
		List<RozaStatement> block1 = Arrays.asList(statement1);
		List<RozaStatement> block2 = Arrays.asList(statement2);
		List<List<RozaStatement>> blocks = Arrays.asList(block1, block2);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithEqualStatement() throws Exception {
		RozaStatement statement1 = new RozaStatement("System.out.println(0);");
		RozaStatement statement2 = new RozaStatement("System.out.println(0);");
		List<RozaStatement> block1 = Arrays.asList(statement1);
		List<RozaStatement> block2 = Arrays.asList(statement2);
		List<List<RozaStatement>> blocks = Arrays.asList(block1, block2);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement2, join.get(0));
	}

	@Test
	void twoBlocksWithOneEqualAndOneDistinctStatement() throws Exception {
		RozaStatement statement1 = new RozaStatement("System.out.println(0);");
		RozaStatement statement2 = new RozaStatement("System.out.println(1);");
		RozaStatement statement3 = new RozaStatement("System.out.println(0);");
		RozaStatement statement4 = new RozaStatement("System.out.println(2);");
		List<RozaStatement> block1 = Arrays.asList(statement1, statement2);
		List<RozaStatement> block2 = Arrays.asList(statement3, statement4);
		List<List<RozaStatement>> blocks = Arrays.asList(block1, block2);
		List<RozaStatement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement3, join.get(0));
	}

}
