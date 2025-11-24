package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteSistemaBancario { 

	@Test
	public void criarBanco(){ //inline 
	 //Fixture Setup
	 SistemaBancario sistBanc = new SistemaBancario();
	 //Exercise SUT
	 Banco bb = sistBanc.criarBanco("Banco do Brasil", Moeda.BRL);
	 //Result Verification
	 assertEquals(1, sistBanc.obterBancos().size());
	 //Fixture Teardown
	}
	
	@Test
	public void criarBancoNomeVazio(){ //inline 
	 //Fixture Setup
	 SistemaBancario sistBanc = new SistemaBancario();
	 //Exercise SUT
	 Banco bb = sistBanc.criarBanco("", Moeda.BRL);
	 //Result Verification
	 fail("Não implementado o tratamento de exceção ao gerar banco com nome vazio");
	 //Fixture Teardown
	}
}
