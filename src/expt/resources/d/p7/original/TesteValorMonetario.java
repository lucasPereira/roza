package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TesteValorMonetario {

	Dinheiro reais_100;
	
	@Before // Implicit SetUp
	public void setUp() throws Exception {
		reais_100 = new Dinheiro(Moeda.BRL,110,00);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void criaValorMonetarioNulo() {
		// Exercise SUT
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		// Result Verification
		assertEquals(0,vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}
	
	@Test // In-Line SetUp
	public void somaValor() {
		// In-Line SetUp
		Dinheiro reais_100 = new Dinheiro(Moeda.BRL,100,00);
		// Exercise SUT
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_100);
		// Result Verification
		assertEquals(10000,vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}

	@Test // In-Line SetUp
	public void subtraiValor_InLine() {
		// In-Line SetUp
		Dinheiro reais_300 = new Dinheiro(Moeda.BRL,300,00);
		Dinheiro reais_150 = new Dinheiro(Moeda.BRL,150,00);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_300);
		// Exercise SUT
		vm = new ValorMonetario(Moeda.BRL).subtrair(reais_150);
		// Result Verification
		assertEquals(15000,vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}

	@Test // Implicit and In-Line SetUp
	public void subtraiValor_MixedSetUp() {
		// Implicit SetUp: reais_100
		// In-Line SetUp
		Dinheiro reais_300 = new Dinheiro(Moeda.BRL,300,00);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_300);
		// Exercise SUT
		vm = new ValorMonetario(Moeda.BRL).subtrair(this.reais_100);
		// Result Verification
		assertEquals(11000,vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}
	
	@Test // Delegated SetUp
	public void getQuantia() {
		// Delegated SetUp
		Dinheiro dinheiro = TesteValorMonetarioHelper.criaDinheiro();
		// Exercise SUT
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(dinheiro);
		// Result Verification
		assertEquals(new Dinheiro(Moeda.BRL,100,00),vm.obterQuantia());
	}
	
	
}
