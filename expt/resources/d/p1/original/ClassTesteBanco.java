package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.assertEquals;
import org.junit.Test;


public class ClassTesteBanco {

	@Test
	public void testeCriaBanco() {
		//In-Line Fixture Setup
		String nome_banco = "Universitario";
		Moeda moeda_banco = Moeda.BRL;
		Dinheiro dinheiro_banco = new Dinheiro(moeda_banco, 100000, 0);
		
		//Exercise SUT
		Banco banco_universitario = new Banco(nome_banco, moeda_banco, dinheiro_banco);
		
		//Result Verification
		assertEquals(nome_banco, banco_universitario.obterNome());
		assertEquals(moeda_banco, banco_universitario.obterMoeda());
		assertEquals(dinheiro_banco, banco_universitario.obterDinheiro());
		
		//Fixture Teardown
		
	}
	
	@Test
	public void testeObtemNomeBanco() {
		//Delegated Fixture Setup
		Banco banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		
		//Exercise SUT
		String nome_banco = banco_universitario.obterNome();
		
		//Result Verification
		assertEquals("Universitario", nome_banco);
		
		//Fixture Teardown		
		
	}
	
	@Test
	public void testeObtemMoedaBanco() {
		//Delegated Fixture Setup
		Banco banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		
		//Exercise SUT
		Moeda moeda_banco = banco_universitario.obterMoeda();
		
		//Result Verification
		assertEquals(Moeda.BRL, moeda_banco);
		
		//Fixture Teardown		
		
	}
	
	@Test
	public void testeObtemDinheiroBanco() {
		//Delegated Fixture Setup
		Banco banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
		
		//Exercise SUT
		Dinheiro dinheiro_banco = banco_universitario.obterDinheiro();
		Dinheiro dinheiro_banco_valor = new Dinheiro(Moeda.BRL, 100000, 0);
		
		//Result Verification
		assertEquals(dinheiro_banco_valor, dinheiro_banco);
		
		//Fixture Teardown		
		
	}
	
}
