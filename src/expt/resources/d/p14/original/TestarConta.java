package sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestarConta {
	
	//Delegated Setup
	public static Agencia criarAgencia() {
		Dinheiro milReais = new Dinheiro(Moeda.BRL, 1000, 0);
		Banco bradesco = new Banco("Bradesco", Moeda.BRL, milReais);
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		return agenciaTrindade;
	}

	@Test
	public void criarContaCorrenteLuisFernando() {
		//Fixture Setup
		criarAgencia();
		Agencia agenciaTrindade = criarAgencia();
		//Exercise SUT
		Conta contaCorrenteLuisFernando = new Conta("Luis Fernando", 1, agenciaTrindade);
		//Result Verification
		assertEquals("Luis Fernando", contaCorrenteLuisFernando.obterTitular());
		//Fixture Teardown
	}
	
	@Test
	public void obterTitularContaCorrenteLuisFernando() {
		//Fixture Setup
		criarAgencia();
		Agencia agenciaTrindade = criarAgencia();
		//Exercise SUT
		Conta contaCorrenteLuisFernando = new Conta("Luis Fernando", 1, agenciaTrindade);
		//Result Verification
		assertEquals("Luis Fernando", contaCorrenteLuisFernando.obterTitular());
		//Fixture Teardown
	}
	
	@Test
	public void obterAgenciaContaCorrenteLuisFernando() {
		//Fixture Setup
		criarAgencia();
		Agencia agenciaTrindade = criarAgencia();
		//Exercise SUT
		Conta contaCorrenteLuisFernando = new Conta("Luis Fernando", 1, agenciaTrindade);
		//Result Verification
		assertEquals(agenciaTrindade, contaCorrenteLuisFernando.obterAgencia());
		//Fixture Teardown
	}

}
