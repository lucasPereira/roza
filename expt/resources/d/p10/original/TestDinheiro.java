package test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;

public class TestDinheiro {

	@Test
	public void testCriaDinheiroPositivo() {
		// Fixture setup
		// Exercise SUT
		Dinheiro dinheiroCemReais = new Dinheiro(Moeda.BRL, 100, 0);
		// Result Verification
		assertEquals(10000, dinheiroCemReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dinheiroCemReais.obterMoeda());
		assertEquals("+100,00 BRL", dinheiroCemReais.positivo().formatado());
		// Fixture teardown
	}
	
	@Test
	public void testCriaDinheiroNegativo() {
		// Fixture setup
		// Exercise SUT
		Dinheiro dinheiroCemReaisNegativo = new Dinheiro(Moeda.BRL, -100, 0);
		// Result Verification
		assertEquals(10000, dinheiroCemReaisNegativo.obterQuantiaEmEscala().intValue());
		assertEquals("-100,00 BRL", dinheiroCemReaisNegativo.negativo().formatado());
		// Fixture teardown
	}

}
