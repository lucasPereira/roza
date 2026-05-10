package test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;

public class TestAgencia {

	
	@Test
	public void testCriarAgencia() {
		// Fixture Setup: delegated
		Banco bancoBradesco = TestHelper.criarBancoBradesco();
		// Exercise SUT
		Agencia agenciaUFSC = bancoBradesco.criarAgencia("UFSC");
		// Result Verification
		assertEquals("001", agenciaUFSC.obterIdentificador());
		assertEquals("UFSC", agenciaUFSC.obterNome());
		assertEquals(bancoBradesco, agenciaUFSC.obterBanco());
		// Fixture Teardown
	}
}
