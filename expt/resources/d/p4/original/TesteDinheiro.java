package teste;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;

public class TesteDinheiro {
	@Test
	public void criarDinheiroEVerificarMoeda() {
		// Fixture Setup
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		
		// Result Verification
		assertEquals(dinheiro.obterMoeda(), Moeda.BRL);
	}
	@Test
	public void criarDinheiroEVerificarQuantiaZero() {
		// Fixture Setup
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
		
		// Result Verification
		assertEquals(dinheiro.obterQuantiaEmEscala().intValue(), 0);
	}
}
