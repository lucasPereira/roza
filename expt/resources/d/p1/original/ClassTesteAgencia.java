package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class ClassTesteAgencia {
	
	private String nome_agencia = "UFSC";
	private Integer codigo_agencia = 1;
	
	private Banco banco_universitario;

	// Fixture Setup: Implicit Setup + Delegated Setup
	@Before
	public void setUpCriaBanco() {
		
		banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();

	}
	
	@Test
	public void criaAgencia() {
		 //Exercise SUT
				Agencia agencia_ufsc = new Agencia(nome_agencia, codigo_agencia, banco_universitario);
		 //Result Verification
				assertEquals(nome_agencia, agencia_ufsc.obterNome());
				assertEquals("00" + codigo_agencia.toString(), agencia_ufsc.obterIdentificador());
				assertEquals(banco_universitario, agencia_ufsc.obterBanco());
		 //Fixture Teardown
	}
	
	/* - Executado em outra classe para melhorar divisão e entendimento de código
	@Test
	public void criaConta() {


	}
	*/
	
	
}
