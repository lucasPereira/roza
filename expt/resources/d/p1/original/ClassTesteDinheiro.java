package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ClassTesteDinheiro {

	private Dinheiro dinheiro_banco;
	private Moeda moeda_dinheiro = Moeda.BRL;
	private Integer qtde_dinheiro = 100000;
	
	// Fixture Setup: Implicit Setup
	@Before
	public void setUpCriaDinheiro() {
		
		dinheiro_banco = new Dinheiro(moeda_dinheiro, qtde_dinheiro, 0);

	}
	
	@Test
	public void testObterMoeda() {
		 //Exercise SUT
			Moeda moeda_local = dinheiro_banco.obterMoeda(); 

		 //Result Verification
			assertEquals(moeda_dinheiro, moeda_local);

		 //Fixture Teardown
	}
	
	
	@Test
	public void testValorMonetarioPositivo() {

		 //Exercise SUT
			ValorMonetario valormonetario_positivo = dinheiro_banco.positivo();
			ValorMonetario valor_monetario_setup = new ValorMonetario(moeda_dinheiro);

		 //Result Verification
			assertEquals(valor_monetario_setup, valormonetario_positivo);

		 //Fixture Teardown
	}
	
	
	
	
}
