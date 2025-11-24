package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;

public class TesteDinheiro {

	@Test
	public void criarDinheiro() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 0 , 0);
		 //Exercise SUT
		 Moeda moeda = dindin.obterMoeda();
		 //Result Verification
		 assertEquals(Moeda.BRL, moeda);
		 //Fixture Teardown
	}
	
	public void criarDinheiro2() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 1 , 50);
		 //Exercise SUT
		 Moeda moeda = dindin.obterMoeda();
		 //Result Verification
		 assertEquals(Moeda.BRL, moeda);
		 //Fixture Teardown
	}

	@Test
	public void criarDinheiroFracionadoNegativo() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 0 , -10);
		 //Exercise SUT
		 String quantia = dindin.formatado();
		 //Result Verification
		 fail("Não implementado o tratamento de exceção ao gerar Dinheiro com fracionado negativo");
		 //Fixture Teardown
	}
	
	
	@Test
	public void positivo() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 50 , 2);
		 //Exercise SUT
		 ValorMonetario vm = dindin.positivo();
		 //Result Verification
		 assertEquals("+50,02 BRL", vm.formatado());
		 //Fixture Teardown
	}
	
	@Test
	public void negativo() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 50 , 2);
		 //Exercise SUT
		 ValorMonetario vm = dindin.negativo();
		 //Result Verification
		 assertEquals("-50,02 BRL", vm.formatado());
		 //Fixture Teardown
	}
	
	@Test
	public void equalsDinheiroTrue() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 50 , 2);
		 Dinheiro outro = new Dinheiro(Moeda.BRL, 50 , 2);
		 //Exercise SUT
		 Boolean igual = dindin.equals(outro);
		 //Result Verification
		 assertEquals(true, igual);
		 //Fixture Teardown
	}
	
	@Test
	public void equalsDinheiroFalse() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 50 , 2);
		 Dinheiro outro = new Dinheiro(Moeda.BRL, 1000 , 0);
		 //Exercise SUT
		 Boolean igual = dindin.equals(outro);
		 //Result Verification
		 assertEquals(false, igual);
		 //Fixture Teardown
	}
	
	@Test
	public void equalsDinheiroDiferentesObjetos() { //inline
		 //Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 50 , 2);
		 //Exercise SUT
		 Boolean igual = dindin.equals(Moeda.BRL);
		 //Result Verification
		 assertEquals(false, igual);
		 //Fixture Teardown
	}
	
}
