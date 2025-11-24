package testes;
import br.ufsc.ine.leb.sistemaBancario.*;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesteDinheiro {

	@Test
	public void testeCriacao50Centavos() {
		//Fixture SetUp
		//Exercise SUT
		Dinheiro cinquentaCentavos = new Dinheiro(Moeda.BRL, 0, 50);
		//Results Verification
		assertEquals("0,50 BRL", cinquentaCentavos.formatado());
		assertEquals(50, cinquentaCentavos.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, cinquentaCentavos.obterMoeda());
		//Fixture TearDown
	}
	
	@Test
	public void testeCriacao10reais() {
		//Fixture SetUp
		//Exercise SUT
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		//Results Verification
		assertEquals("10,00 BRL", dezReais.formatado());
		assertEquals(1000, dezReais.obterQuantiaEmEscala().intValue());
		assertEquals(Moeda.BRL, dezReais.obterMoeda());
		assertEquals("-10,00 BRL",dezReais.negativo().formatado());
		//Fixture TearDown
	}
	
	@Test
	public void testeObterEmEscalaValorPositivo() {
		//Fixture SetUp
		Dinheiro dezECinquenta = new Dinheiro(Moeda.BRL, 10, 50);
		//Exercise SUT
		Integer valorEmEscala = dezECinquenta.obterQuantiaEmEscala();
		//Results Verification
		assertEquals(1050, valorEmEscala.intValue());
		//Fixture TearDown
	}

}
