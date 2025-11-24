package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Nomes de classes SEMPRE começam com letra maiúsculas
public class TesteDinheiro {

	// Inicializacao das variaveis utilizadas
	Dinheiro reais_100;
	Dinheiro reais_50;
	
	@Before // Implicit Fixture SetUp
	public void setUp() throws Exception {
		// Instancia de Dinheiro com 100 reais e 00 centavos
		reais_100 = new Dinheiro(Moeda.BRL,100,00);
		// Instancia de Dinheiro com 50 reais e 00 centavos
		reais_50 = new Dinheiro(Moeda.BRL,50,00);
	}

	@Test // Implicit Fixture SetUp
	public void criaDinheiro() {
		// Exercise SUT
		Dinheiro reais = new Dinheiro(Moeda.BRL,100,00);
		// Result Verification
		assertEquals(true,reais.equals(this.reais_100));
	}
	
	@Test // Implicit Fixture SetUp
	public void criaDinheiroDiferente() {
		// Result Verification
		assertNotEquals(true,this.reais_50.equals(this.reais_100));
	}
	
	@Test // Implicit Fixture SetUp
	public void criaDinheiro_FracionadoNegativo() {
		// Exercise SUT
		Dinheiro reais = new Dinheiro(Moeda.BRL,100,00);
		// Result Verification
		assertEquals(true,reais.equals(this.reais_100));
	}
	
	@Test // In-Line Fixture SetUp
	public void teste_GetMoeda() {
		// In-Line Fixture SetUp
		Dinheiro reais = new Dinheiro(Moeda.BRL,100,00);
		// Exercise SUT
		Moeda moeda_reais = reais.obterMoeda();
		// Result Verification
		assertEquals(Moeda.BRL,moeda_reais);
	}
	
	@Test // Implicit Fixture SetUp
	public void teste_GetValorEmEscala() {
		// Exercise SUT
		Integer valorEmEscala = reais_100.obterQuantiaEmEscala();
		Moeda moeda = reais_100.obterMoeda();
		// Result Verification
		assertEquals(Moeda.BRL,moeda);
		assertEquals(10000,valorEmEscala.intValue());
	}
	
	@Test // Delegated SetUp
	public void criaDinheiro_DelegatedSetup() {
		// Delegated SetUp
		Dinheiro reais = TesteDinheiroHelper.criaDinheiro();
		// Exercise SUT
		Moeda moeda_reais = reais.obterMoeda();
		// Result Verification
		assertEquals(Moeda.BRL,moeda_reais);
		assertEquals(true,reais.equals(this.reais_100));
	}
	
	@After // Fixture TearDown (Implicit)
	public void tearDown() throws Exception {
	}
}
