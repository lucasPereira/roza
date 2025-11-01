package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Statement;

public class StatementJoinnerTest {

	private StatementJoiner joiner;

	@BeforeEach
	void setup() {
		joiner = new StatementJoiner();
	}

	@Test
	void empty() {
		List<List<Statement>> blocks = List.of();
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneEmptyBlock() {
		List<Statement> block = List.of();
		List<List<Statement>> blocks = List.of(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithOneStatement() {
		Statement statement = new Statement("System.out.println(0);");
		List<Statement> block = List.of(statement);
		List<List<Statement>> blocks = List.of(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithTwoStatements() {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		List<Statement> block = List.of(statement1, statement2);
		List<List<Statement>> blocks = List.of(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithDistinctStatements() {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		List<Statement> block1 = List.of(statement1);
		List<Statement> block2 = List.of(statement2);
		List<List<Statement>> blocks = List.of(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithEqualStatement() {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(0);");
		List<Statement> block1 = List.of(statement1);
		List<Statement> block2 = List.of(statement2);
		List<List<Statement>> blocks = List.of(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement2, join.get(0));
	}

	@Test
	void twoBlocksWithOneEqualAndOneDistinctStatement() {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		Statement statement3 = new Statement("System.out.println(0);");
		Statement statement4 = new Statement("System.out.println(2);");
		List<Statement> block1 = List.of(statement1, statement2);
		List<Statement> block2 = List.of(statement3, statement4);
		List<List<Statement>> blocks = List.of(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement3, join.get(0));
	}

}
