package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteConta {

	@Test
	public void checarTitularConta(){ //Delegated
	 //Fixture Setup
	 Conta contaKarla = TesteAjudante.makeContaKarla();
	 //Exercise SUT
	 String nome = contaKarla.obterTitular();
	 //Result Verification
	 assertEquals("Karla Aparecida Justen", nome);
	 //Fixture Teardown
	}

	@Test
	public void checaAgenciaConta(){ //Delegated
	 //Fixture Setup
	 Conta contaKarla = TesteAjudante.makeContaKarla();
	 //Exercise SUT
	 Boolean agenciaIgual = contaKarla.obterAgencia().obterNome().equals("BB Trindade");
	 //Result Verification
	 assertEquals(true, agenciaIgual);
	 //Fixture Teardown
	}
	
	@Test
	public void calcularSaldoConta(){ //Delegated e Inline
	 //Fixture Setup
	 Conta contaKarla = TesteAjudante.makeContaKarla();
	 Dinheiro quantia = new Dinheiro(Moeda.BRL, 1000, 50);
	 Transacao entrada = new Entrada(contaKarla, quantia);
	 //Exercise SUT
	 contaKarla.adicionarTransacao(entrada);
	 //Result Verification
	 assertEquals("+1000,50 BRL", contaKarla.calcularSaldo().formatado());
	 //Fixture Teardown
	}
	
	
}
