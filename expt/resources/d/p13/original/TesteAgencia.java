package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.*;

public class TesteAgencia {

	private Agencia trindade;
	
	@Before
	public void preparaTeste() {
		Banco bancoDoBrasil = AjudanteDeTestes.retornaBancoDoBrasil();
		trindade = bancoDoBrasil.criarAgencia("Trindade");
	}
	
	@Test
	public void testeCriaConta() {
		//Fixture SetUp - implicit
		//Exercise SUT
		Conta contaJohn = trindade.criarConta("John");
		//Results Verification
		assertEquals("John", contaJohn.obterTitular());
		assertEquals(trindade, contaJohn.obterAgencia());
		//Fixture TearDown
	}

}
