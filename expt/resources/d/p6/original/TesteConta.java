package teste.br.ufsc.ine.leb.sistemaBancario.banco;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Conta;
import teste.br.ufsc.ine.leb.sistemaBancario.helpers.TesteHelper;

public class TesteConta {

	@Test
	public void criaConta() {
		// Fixture Setup - Inline Setup - Delegated Setup
		String nomeDaAgencia = new String("Agencia Trindade");
		String nomeDoTitular = new String("Fulano");
		Agencia agencia = TesteHelper.criaAgencia(nomeDaAgencia);
		
		// Exercise SUT
		Conta conta = agencia.criarConta(nomeDoTitular);
		
		// Result Verification
		assertEquals(agencia, conta.obterAgencia());
		assertEquals(nomeDoTitular, conta.obterTitular());
		assertEquals(new String("0001-6"),  conta.obterIdentificador());
		
		// Fixture Teardown
	}
}
