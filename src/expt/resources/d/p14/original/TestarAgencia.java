package sistemaBancario;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestarAgencia {
	
	Banco bradesco;

	//Implicit Setup 
	@Before
	public void preDefinicoes() {
		Dinheiro milReais = new Dinheiro(Moeda.BRL, 1000, 0);
		bradesco = new Banco("Bradesco", Moeda.BRL, milReais);
	}
	
	@Test
	public void criarAgenciaTrindade() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		//Result Verification
		assertEquals("Trindade", agenciaTrindade.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarNomeAgenciaTrindade() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		//Result Verification
		assertEquals("Trindade", agenciaTrindade.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarBancoAgenciaTrindade() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		//Result Verification
		assertEquals(bradesco, agenciaTrindade.obterBanco());
		//Fixture Teardown
	}

}
