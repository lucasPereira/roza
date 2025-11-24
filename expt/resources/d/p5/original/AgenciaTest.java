package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

//inline tests
public class AgenciaTest {

	Agencia normalAgency;
	
	@Before
	public void init(){
		Banco banco = new Banco ("Banco do Brasil", Moeda.BRL, new Dinheiro(Moeda.BRL, 0,0));
		normalAgency = banco.criarAgencia("Correio");
	}
	
	@Test
	public void criarConta() {
		Conta conta = normalAgency.criarConta("wagner");
		assertEquals( conta.obterAgencia() , normalAgency);
	}
	

}
