package br.ufsc.ine.leb.roza.refactoring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
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
	void empty() throws Exception {
		List<List<Statement>> blocks = Arrays.asList();
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneEmptyBlock() throws Exception {
		List<Statement> block = Arrays.asList();
		List<List<Statement>> blocks = Arrays.asList(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithOneStatement() throws Exception {
		Statement statement = new Statement("System.out.println(0);");
		List<Statement> block = Arrays.asList(statement);
		List<List<Statement>> blocks = Arrays.asList(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void oneBlockWithTwoStatements() throws Exception {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		List<Statement> block = Arrays.asList(statement1, statement2);
		List<List<Statement>> blocks = Arrays.asList(block);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithDistinctStatements() throws Exception {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		List<Statement> block1 = Arrays.asList(statement1);
		List<Statement> block2 = Arrays.asList(statement2);
		List<List<Statement>> blocks = Arrays.asList(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(0, join.size());
	}

	@Test
	void twoBlocksWithEqualStatement() throws Exception {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(0);");
		List<Statement> block1 = Arrays.asList(statement1);
		List<Statement> block2 = Arrays.asList(statement2);
		List<List<Statement>> blocks = Arrays.asList(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement2, join.get(0));
	}

	@Test
	void twoBlocksWithOneEqualAndOneDistinctStatement() throws Exception {
		Statement statement1 = new Statement("System.out.println(0);");
		Statement statement2 = new Statement("System.out.println(1);");
		Statement statement3 = new Statement("System.out.println(0);");
		Statement statement4 = new Statement("System.out.println(2);");
		List<Statement> block1 = Arrays.asList(statement1, statement2);
		List<Statement> block2 = Arrays.asList(statement3, statement4);
		List<List<Statement>> blocks = Arrays.asList(block1, block2);
		List<Statement> join = joiner.join(blocks);

		assertEquals(1, join.size());
		assertEquals(statement1, join.get(0));
		assertEquals(statement3, join.get(0));
	}

}
