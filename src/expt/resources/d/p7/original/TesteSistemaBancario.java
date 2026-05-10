package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

public class TesteSistemaBancario {

	// Inicializacao
	SistemaBancario SB;
	Banco BB;
	Agencia Trindade;
	Agencia Agronomica;
	Conta contaIvan;
	//Conta contaGuilherme;
	Dinheiro reais_00;
	Dinheiro reais_50;
	Dinheiro reais_100;
	
	// Mesmo sabendo que o ideal e utilizar o Implicit SetUp para diminuir a repeticao de
	// codigo comum a maioria dos testes, aqui sao realizadas chamadas que nao necessariamente
	// sao essenciais a todos os testes somente para facilitar a demonstracao deste tipo de SetUp.
			
	@Before // Implicit Fixture SetUp 
	public void setUp() throws Exception {
		this.SB = new SistemaBancario();
		this.BB = this.SB.criarBanco("BB", Moeda.BRL);
		this.Agronomica = this.BB.criarAgencia("Agronomica");
		this.Trindade = this.BB.criarAgencia("Trindade");
		this.contaIvan = this.Agronomica.criarConta("Ivan");
		this.reais_00 = new Dinheiro(Moeda.BRL,00,00);
		this.reais_50 = new Dinheiro(Moeda.BRL,50,00);
		this.reais_100 = new Dinheiro(Moeda.BRL,100,00);
	}

	@Test // In-Line Fixture SetUp
	public void criaSistemaBancario() {
		// Exercise SUT
		SistemaBancario SistBancario = new SistemaBancario();
		// Result Verification
		assertEquals(SistBancario.obterBancos(),new LinkedList<>());
	}
	
	@Test // Implicit Fixture SetUp
	public void adicionaBanco() {
		// Exercise SUT
		Banco Bradesco = this.SB.criarBanco("Bradesco", Moeda.BRL);
		// Result Verification
		assertEquals("Bradesco", Bradesco.obterNome());
		assertEquals(Moeda.BRL, Bradesco.obterMoeda());
	}

	@Test // Implicit Fixture SetUp
	public void comparaBancos_mesmoNome() {
		// In-Line Fixture SetUp & Exercise SUT
		Banco Bradesco = this.SB.criarBanco("Bradesco", Moeda.BRL);
		Banco BradescoInternational = this.SB.criarBanco("Bradesco", Moeda.USD);
		// Result Verification
		assertNotEquals(Bradesco, BradescoInternational);
	}
	
	@Test // Implicit Fixture SetUp
	public void testeSaldoInicial() {
		// Result Verification
		assertEquals(this.reais_00.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
	}
	
	@Test // Implicit Fixture SetUp
	public void testeDeposito() {
		// Exercise SUT
		this.SB.depositar(contaIvan, reais_100);
		// Result Verification
		assertEquals(this.reais_100.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
	}
	
	@Test // Implicit & In-Line Fixture SetUp
	public void testeSaque() {
		// In-Line Fixture SetUp
		this.SB.depositar(contaIvan, reais_100);
		// Exercise SUT
		this.SB.sacar(contaIvan, reais_50);
		// Result Verification
		assertEquals(this.reais_50.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
	}
	
	@Test // Implicit & In-Line Fixture SetUp
	public void testeSaque_saldoInsuficiente() {
		// In-Line Fixture SetUp
		this.SB.depositar(contaIvan, reais_50);
		// Exercise SUT
		this.SB.sacar(contaIvan, reais_100);
		// Result Verification
		assertEquals(this.reais_50.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
	}
	
	@Test // Implicit & In-Line Fixture SetUp
	public void teste_transferencia() {
		// In-Line Fixture SetUp
		Conta contaGuilherme = this.Trindade.criarConta("Guilherme");
		this.SB.depositar(contaIvan, reais_100);
		// Exercise SUT
		this.SB.transferir(contaIvan, contaGuilherme, reais_50);
		// Result Verification 
		assertEquals(this.reais_50.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
		assertEquals(this.reais_50.formatado(),contaGuilherme.calcularSaldo().formatarSemSinal());
	}
	
	@Test // Implicit & In-Line Fixture SetUp
	public void testeTransferencia_saldoNegativo() {
		// In-Line Fixture SetUp
		Conta contaGuilherme = this.Trindade.criarConta("Guilherme");
		this.SB.depositar(contaGuilherme, reais_50);
		
		// Exercise SUT
		//Operacao transacao1 = 
		this.SB.transferir(contaGuilherme, contaIvan, reais_100);
//		System.out.println(transacao1.obterEstado());
//		System.out.println(contaGuilherme.calcularSaldo().formatarComSinal());
//		System.out.println(contaIvan.calcularSaldo().formatarComSinal());
			
		// Result Verification 
		assertEquals(this.reais_100.formatado(),this.contaIvan.calcularSaldo().formatarSemSinal());
		assertEquals(this.reais_50.negativo().formatarComSinal(),contaGuilherme.calcularSaldo().formatarComSinal());
	}
	
	@Test // Delegated, Implicit & In-Line SetUp
	public void testeTransferencia_delegated() {
		// Implicit SetUp
		// Delegated SetUp
		Conta contaThiago = TesteSistemaBancarioHelper.criaConta(SB, "Santander", "StMonica", "Thiago", 50);
		// In-Line SetUp
		this.SB.depositar(contaIvan, reais_50);
		// Exercise SUT
		this.SB.transferir(contaIvan, contaThiago, reais_50);
		// Result Verification
		assertEquals(this.reais_100.formatado(),contaThiago.calcularSaldo().formatarSemSinal());	
	}
	
//	public void teste
}
