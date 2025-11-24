package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Entrada;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.Saida;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;
import br.ufsc.ine.leb.sistemaBancario.Transacao;

public class Teste_Conta {

	private SistemaBancario sistemaBancario;
	private Banco bancoReal;
	private Agencia agenciaTrindade;
	private Conta joseph;
	
	@Before
	public void setUp() {
		sistemaBancario = new SistemaBancario();
		bancoReal = sistemaBancario.criarBanco("Banco Real", Moeda.BRL);
		agenciaTrindade = bancoReal.criarAgencia("Trindade");
		joseph = agenciaTrindade.criarConta("Joseph");
	}
	
	@Test
	public void criaConta() {
		//Fixture Setup
		//Exercise SUT
		//Results Verification
		assertEquals("Joseph", joseph.obterTitular());
		assertEquals(agenciaTrindade, joseph.obterAgencia());
		assertEquals("0001-6", joseph.obterIdentificador());
		//Fixture TearDown
	}
	
	@Test
	public void testaContaSaldoZeroCriacao() {
		//Fixture Setup
		//Exercise SUT
		//Results Verification
		assertTrue(joseph.calcularSaldo().zero());
		//Fixture TearDown
	}
	
	@Test
	public void testaContaSaldoPositivo() {
		//Fixture Setup
		Dinheiro dezReais = Delegates.criaDezReais();
		Transacao deposito = new Entrada(joseph, dezReais);
		//Exercise SUT
		joseph.adicionarTransacao(deposito);
		//Results Verification
		assertTrue(joseph.calcularSaldo().subtrair(dezReais).zero());
		//Fixture TearDown
	}
	
	@Test
	public void testaContaSaldoNegativo() {
		//Fixture Setup
		Dinheiro dezReais = Delegates.criaDezReais(); 
		Transacao saque = new Saida(joseph, dezReais);
		//Exercise SUT
		joseph.adicionarTransacao(saque);
		//Results Verification
		assertTrue(joseph.calcularSaldo().somar(dezReais).zero());
		//Fixture TearDown
	}

}
