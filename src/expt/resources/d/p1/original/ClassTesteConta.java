package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ClassTesteConta {

	private Banco banco_universitario;
	private Agencia agencia_ufsc;
	private Conta conta_guilherme;
	
	// Fixture Setup: Implicit Setup + Delegated Setup
	@Before
	public void setUpConta() {
		
		banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		agencia_ufsc = TestBancoAgenciaHelper.CriaAgenciaUniversitaria();

	}
	
	@Test
	public void testCriaContaGuilhermeUfsc() {
		 //Exercise SUT
				String titular_conta = "Guilherme";
				conta_guilherme = agencia_ufsc.criarConta(titular_conta);
		 //Result Verification
				assertEquals(titular_conta, conta_guilherme.obterTitular());
				assertEquals(agencia_ufsc, conta_guilherme.obterAgencia());
		 //Fixture Teardown
	}
	
		
	
	
}
