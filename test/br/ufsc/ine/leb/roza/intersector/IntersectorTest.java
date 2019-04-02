package br.ufsc.ine.leb.roza.intersector;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.intersector.Intersector;

public class IntersectorTest {

	@Test
	void empty() throws Exception {
		Intersector<?> intersector = new Intersector<>();
		assertEquals(BigDecimal.ZERO, intersector.evaluate());
		assertEquals(0, intersector.getIntervals().size());
	}

	@Test
	void oneSegment() throws Exception {
		Intersector<String> intersector = new Intersector<>();
		intersector.addSegment("", 1, 10);
		assertEquals(BigDecimal.ONE, intersector.evaluate());
		assertEquals(1, intersector.getIntervals().size());
		assertEquals(1, intersector.getIntervals().get(0).getStart().intValue());
		assertEquals(9, intersector.getIntervals().get(0).getEnd().intValue());
		assertEquals(10, intersector.getIntervals().get(0).getLength().intValue());
	}

}
