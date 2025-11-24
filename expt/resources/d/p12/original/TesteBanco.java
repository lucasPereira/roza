package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteBanco {

	private Banco bb;
	
	@Before
	public void setUp(){
		SistemaBancario sistBanc = new SistemaBancario();
		bb = sistBanc.criarBanco("Banco do Brasil", Moeda.BRL);
	}
	
	@Test
	public void checarNomeBanco(){ //implicit
	 //Fixture Setup
	 //Exercise SUT
	 String nome = bb.obterNome();
	 //Result Verification
	 assertEquals("Banco do Brasil", nome);
	 //Fixture Teardown
	}

	@Test
	public void checarMoedaBanco(){//implicit
	 //Fixture Setup
	 //Exercise SUT
	 Moeda moeda =  bb.obterMoeda();
	 //Result Verification
	 assertEquals(Moeda.BRL,moeda);
	 //Fixture Teardown
	}

	@Test 
	public void checarCriarAgencia(){//implicit
	 //Fixture Setup
	 //Exercise SUT
	 bb.criarAgencia("BB Trindade");
	 bb.criarAgencia("BB Campeche");
	 //Result Verification
	 fail("Não foi implementado método para acessar as agencias do banco");
	 //Fixture Teardown
	}
	
	@Test
	public void CriarAgenciaNomeVazio(){//implicit
	 //Fixture Setup
	 //Exercise SUT
	 bb.criarAgencia("");
	 //Result Verification
	 fail("Não implementado o tratamento de exceção ao gerar agencia com nome vazio");
	 //Fixture Teardown
	}
}
