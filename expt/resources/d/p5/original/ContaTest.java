package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ContaTest {
	Agencia normalAgency;
	
	@Before
	public void init(){
		Banco banco = new Banco ("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0,0));
		normalAgency = banco.criarAgencia("Correio");
	}
	
	@Test
	public void criarConta() {
		Conta minhaConta = new Conta("Wagner",1,normalAgency);	
		assertEquals( minhaConta.obterTitular() , "Wagner") ;
	}
	@Test
	public void criarNConta() {
		for(int i =0 ; i < 10; i++){
			Conta conta = normalAgency.criarConta("Wagner"+i);	
			assertEquals( normalAgency ,conta.obterAgencia() ) ;
		}

	}
	

}
