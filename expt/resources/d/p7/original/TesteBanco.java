package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TesteBanco {

	SistemaBancario SB;
	Banco BB;
	Agencia Trindade;
	Agencia Agronomica;
	Conta conta_Ivan;
	Dinheiro reais_00;
	
	// Mesmo sabendo que o ideal e utilizar o Implicit SetUp para diminuir a repeticao de
	// codigo comum a maioria dos testes, aqui sao realizadas chamadas que nao necessariamente
	// sao essenciais a todos os testes somente para facilitar a demonstracao deste tipo de SetUp.
	
	@Before // Implicit SetUp
	public void setUp() throws Exception {
		this.SB = new SistemaBancario();
		this.BB = this.SB.criarBanco("BB", Moeda.BRL);
		this.Agronomica = this.BB.criarAgencia("Agronomica");
		this.reais_00 = new Dinheiro(Moeda.BRL,0,0);
	}

	@Test // In-Line & Implicit SetUp
	public void criaAgencia() {
		// Exercise SUT
		this.Trindade = this.BB.criarAgencia("Trindade");
		// Result Verification
		assertEquals(Moeda.BRL,this.Trindade.obterBanco().obterMoeda());
		assertEquals("BB", this.Trindade.obterBanco().obterNome());
		assertEquals("Trindade", this.Trindade.obterNome());
		assertEquals("002", this.Trindade.obterIdentificador());
	}

	@Test //In-Line & Implicit SetUp
	public void criaConta() {
		// In-Line SetUp
		this.Trindade = this.BB.criarAgencia("Trindade");
		//Exercise SUT
		this.conta_Ivan = this.Trindade.criarConta("Ivan");
		//Result Verification
		assertEquals(Trindade, this.conta_Ivan.obterAgencia());
		assertEquals("Ivan", this.conta_Ivan.obterTitular());
		assertEquals(this.reais_00.formatado(),this.conta_Ivan.calcularSaldo().formatarSemSinal());
	}

}
