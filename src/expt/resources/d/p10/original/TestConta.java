package test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TestConta {
	
	@Test
	public void testCriarConta() {
		// Fixture setup: Inline and delegated
		Banco bancoBradesco = TestHelper.criarBancoBradesco();
		Agencia agenciaUFSC = bancoBradesco.criarAgencia("UFSC");
		// Exercise SUT
		Conta conta = agenciaUFSC.criarConta("Anna");
		// Result Verification
		assertEquals("0001-4", conta.obterIdentificador());
		assertEquals("Anna", conta.obterTitular());
		assertEquals("0,00", conta.calcularSaldo().formatado());
		assertEquals("UFSC", conta.obterAgencia().obterNome());
		// Fixture Teardown
	}
	
	@Test
	public void testConsultarSaldo() {
		// Fixture setup: Inline and delegated
		Conta conta = TestHelper.criaContaBradesco();
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 10, 0);
		SistemaBancario sistemaBancario = new SistemaBancario();
		
		// Exercise SUT
		sistemaBancario.depositar(conta, dinheiro);
		
		// Result Verification
		assertEquals("+10,00 BRL", conta.calcularSaldo().formatado());
		// Fixture Teardown
	}
	


	
}
