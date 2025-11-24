package sistemaBancario;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class TesteAgencia {
	
	Banco bancoBrasil;

	//Implicit Setup 
	@Before
	public void criarBanco() {
		Dinheiro quinhentosReais = new Dinheiro(Moeda.BRL, 500, 0);
		bancoBrasil = new Banco("Bradesco", Moeda.BRL, quinhentosReais);
	}
	
	@Test
	public void criarAgencia() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		//Result Verification
		assertEquals("Itacorubi", agenciaItacorubi.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarNomeAgencia() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		//Result Verification
		assertEquals("Itacorubi", agenciaItacorubi.obterNome());
		//Fixture Teardown
	}
	
	@Test
	public void verificarBancoAgencia() {
		//Fixture Setup
		//Exercise SUT
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		//Result Verification
		assertEquals(bancoBrasil, agenciaItacorubi.obterBanco());
		//Fixture Teardown
	}

}
