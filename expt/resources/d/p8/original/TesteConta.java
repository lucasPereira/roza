package sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

public class TesteConta {
	
	//Delegated Setup
	public static Agencia criarAgencia() {
		Dinheiro quinhentosReais = new Dinheiro(Moeda.BRL, 500, 0);
		Banco bancoBrasil = new Banco("Brasil", Moeda.BRL, quinhentosReais);
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		return agenciaItacorubi;
	}

	@Test
	public void criarContaCorrente() {
		//Fixture Setup
		Agencia agenciaItacorubi = criarAgencia();
		//Exercise SUT
		Conta contaCorrente = new Conta("Leandro Abrantes", 1, agenciaItacorubi);
		//Result Verification
		assertEquals("Leandro Abrantes", contaCorrente.obterTitular());
		//Fixture Teardown
	}
	
	@Test
	public void obterTitularContaCorrente() {
		//Fixture Setup
		Agencia agenciaItacorubi = criarAgencia();
		//Exercise SUT
		Conta contaCorrente = new Conta("Leandro Abrantes", 1, agenciaItacorubi);
		//Result Verification
		assertEquals("Leandro Abrantes", contaCorrente.obterTitular());
		//Fixture Teardown
	}
	
	@Test
	public void obterAgenciaContaCorrente() {
		//Fixture Setup
		Agencia agenciaItacorubi = criarAgencia();
		//Exercise SUT
		Conta contaCorrente = new Conta("Leandro Abrantes", 1, agenciaItacorubi);
		//Result Verification
		assertEquals(agenciaItacorubi, contaCorrente.obterAgencia());
		//Fixture Teardown
	}

}
