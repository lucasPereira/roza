package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class Teste_ValorMonetario {
	
	private ValorMonetario baseBRL;
	
	@Before
	public void setUp() {
		baseBRL = new ValorMonetario(Moeda.BRL);
	}

	@Test
	public void criaValorMonetarioNegativo() {
		//Fixture SetUp
		Dinheiro dezReais = Delegates.criaDezReais();	
		//Exercise SUT
		ValorMonetario menosDez = baseBRL.subtrair(dezReais);
		//Results Verification
		assertTrue(menosDez.negativo());
		//Fixture TearDown
	}
	
	@Test
	public void criaValorMonetarioPositivo() {
		//Fixture SetUp
		Dinheiro dezReais = Delegates.criaDezReais();
		//Exercise SUT
		ValorMonetario dez = baseBRL.somar(dezReais);
		//Results Verification
		assertFalse(dez.negativo());
		//Fixture TearDown
	}

	@Test
	public void criaValorMonetarioZero() {
		//Fixture SetUp
		//Exercise SUT
		//Results Verification
		assertTrue(baseBRL.zero());
		//Fixture TearDown
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void somaDolarComReal() {
		//Fixture SetUp
		Dinheiro dezDolares = Delegates.criaDezDolares();
		//Exercise SUT
		ValorMonetario dez = baseBRL.somar(dezDolares);
		//Results Verification
		//Fixture TearDown
	}
	
	@Test
	public void stringValorMonetarioNegativo() {
		//Fixture SetUp
		Dinheiro dezReais = Delegates.criaDezReais();
		//Exercise SUT
		ValorMonetario dez = baseBRL.subtrair(dezReais);
		//Results Verification
		assertEquals("-10,00 BRL", dez.toString());
		//Fixture TearDown
	}
	
	@Test
	public void stringValorMonetarioPositivo() {
		//Fixture SetUp
		Dinheiro dezReais = Delegates.criaDezReais();
		//Exercise SUT
		ValorMonetario dez = baseBRL.somar(dezReais);
		//Results Verification
		assertEquals("+10,00 BRL", dez.toString());
		//Fixture TearDown
	}
	
	@Test
	public void stringValorMonetarioZero() {
		//Fixture SetUp
		//Exercise SUT
		//Results Verification
		assertEquals("0,00", baseBRL.toString());
		//Fixture TearDown
	}

}
