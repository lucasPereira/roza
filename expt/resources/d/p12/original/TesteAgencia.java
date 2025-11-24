package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Agencia;
import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteAgencia {

	Banco bb;
	Agencia bbTrindade;
	
	@Before
	public void setUp(){
		 SistemaBancario sistBanc = new SistemaBancario();
		 bb = sistBanc.criarBanco("Banco do Brasil", Moeda.BRL);
		 bbTrindade = bb.criarAgencia("BB Trindade");
	}
	@Test
	public void checarNomeAgencia(){ // implicit
	 //Fixture Setup
	 //Exercise SUT
	 String teste = bbTrindade.obterNome();
	 //Result Verification
	 assertEquals("BB Trindade", teste);
	 //Fixture Teardown
	}
	
	@Test
	public void checarBancoDaAgencia(){ // implicit
	 //Fixture Setup
	 //Exercise SUT
	 Banco teste = bbTrindade.obterBanco();
	 //Result Verification
	 assertEquals(bb, teste);
	 //Fixture Teardown
	}
	
	@Test
	public void criarConta(){ // implicit
	 //Fixture Setup
	 //Exercise SUT
	 bbTrindade.criarConta("Karla Aparecida Justen");
	 //Result Verification
	 fail("Não foi implementado método para acessar as contas da Agencia");
	 //Fixture Teardown
	}
	
	@Test
	public void criarContaNomeVazio(){ // implicit
	 //Fixture Setup
	 //Exercise SUT
	 bbTrindade.criarConta("");
	 //Result Verification
	 fail("Não implementado o tratamento de exceção ao gerar agencia com nome vazio");
	 //Fixture Teardown
	}
}
