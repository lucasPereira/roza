import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class testeEnums {
	
	@Test
	public void testeTipo() {
		
		assertEquals(3, Tipo.values().length);
		assertEquals(Tipo.BUG, Tipo.values()[0]);
		assertEquals(Tipo.MELHORIA, Tipo.values()[1]);
		assertEquals(Tipo.TAREFA, Tipo.values()[2]);
		
	}
	
	@Test
	public void testeEstado() {
		
		assertEquals(2, Estado.values().length);
		assertEquals(Estado.ABERTO, Estado.values()[0]);
		assertEquals(Estado.FECHADO, Estado.values()[1]);
		
	}
	
	@Test
	public void testePrioridade() {
		
		assertEquals(3, Prioridade.values().length);
		assertEquals(Prioridade.ALTA, Prioridade.values()[0]);
		assertEquals(Prioridade.MEDIA, Prioridade.values()[1]);
		assertEquals(Prioridade.BAIXA, Prioridade.values()[2]);
	}
}
