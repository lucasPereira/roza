package br.ufsc.ine.leb.roza.retrieval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class StandardRecallLevelsTest {

	@Test
	void iterator() throws Exception {
		StandardRecallLevels standard = new StandardRecallLevels();
		Iterator<RecallLevel> iterator = standard.iterator();
		assertEquals(new RecallLevel(0), iterator.next());
		assertEquals(new RecallLevel(1), iterator.next());
		assertEquals(new RecallLevel(2), iterator.next());
		assertEquals(new RecallLevel(3), iterator.next());
		assertEquals(new RecallLevel(4), iterator.next());
		assertEquals(new RecallLevel(5), iterator.next());
		assertEquals(new RecallLevel(6), iterator.next());
		assertEquals(new RecallLevel(7), iterator.next());
		assertEquals(new RecallLevel(8), iterator.next());
		assertEquals(new RecallLevel(9), iterator.next());
		assertEquals(new RecallLevel(10), iterator.next());
		assertFalse(iterator.hasNext());
	}

}
