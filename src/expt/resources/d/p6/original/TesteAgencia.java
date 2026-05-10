package teste.br.ufsc.ine.leb.sistemaBancario.banco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import teste.br.ufsc.ine.leb.sistemaBancario.helpers.TesteHelper;

public class TesteAgencia {

	@Test
	public void criaAgencia() {
		// Fixture Setup - Delegated Setup - Inline Setup
		Banco bancoDoBrasil = TesteHelper.criaBancoDoBrasil();
		String nomeDaAgencia = "Agencia da Trindade";
		
		// Exercise SUT
		Agencia agenciaTrindade = bancoDoBrasil.criarAgencia(nomeDaAgencia);
		
		// Result Verification
		assertEquals(nomeDaAgencia, agenciaTrindade.obterNome());
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(new String("001"), agenciaTrindade.obterIdentificador());
		
		// Fixture Teardown
	}
}
