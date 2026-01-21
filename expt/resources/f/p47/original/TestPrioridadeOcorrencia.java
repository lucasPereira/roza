package tdd.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import tdd.PrioridadeOcorrencia;

public class TestPrioridadeOcorrencia {

	@Test
	public void testHighPriority() {
		int highPriorityValue = PrioridadeOcorrencia.ALTA.getPrioridade();
		
		assertEquals(highPriorityValue, 2);
	}
	
	@Test
	public void testNormalPriority() {
		int normalPriorityValue = PrioridadeOcorrencia.MEDIA.getPrioridade();
		
		assertEquals(normalPriorityValue, 1);
	}
	
	@Test
	public void testLowPriority() {
		int lowPriorityValue = PrioridadeOcorrencia.BAIXA.getPrioridade();
		
		assertEquals(lowPriorityValue, 0);
	}
}
