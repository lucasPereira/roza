package br.ufsc.ine.leb.sistemaBancario;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteValorMonetario { 

	ValorMonetario vmBRL;
	ValorMonetario vmCHF;
	
	@Before
	public void setUp(){
		vmBRL = new ValorMonetario(Moeda.BRL);
		vmCHF = new ValorMonetario(Moeda.CHF);
	}
	
	@Test
	public void criarValorMonetário() { //Implicit
		//Fixture Setup
		 //Exercise SUT
		 //Result Verification
		 assertEquals(true, vmBRL.zero());
		 //Fixture Teardown
	}
	
	@Test
	public void somarValorMonetário() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 50);
		 //Exercise SUT
		 vmBRL = vmBRL.somar(dindin);
		 //Result Verification
		 assertEquals("100,50 BRL", vmBRL.formatarSemSinal());
		 //Fixture Teardown
	}
	
	@Test (expected=UnsupportedOperationException.class)
	public void somarValorMonetárioDiferenteMoedas() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.CHF, 100, 0);
		 //Exercise SUT
		 vmBRL = vmBRL.somar(dindin);
		 //Result Verification
		 //Fixture Teardown
	}
	
	@Test
	public void subtrairValorMonetário() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		 //Exercise SUT
		 vmBRL = vmBRL.subtrair(dindin);
		 //Result Verification
		 assertEquals("-100,00 BRL", vmBRL.formatarComSinal());
		 //Fixture Teardown
	}
	
	@Test (expected=UnsupportedOperationException.class) //Implicit  //inline
	public void subtrairValorMonetárioDiferenteMoedas() {
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.CHF, 100, 0);
		 //Exercise SUT
		 vmBRL = vmBRL.subtrair(dindin);
		 //Result Verification
		 //Fixture Teardown
	}
	
	@Test
	public void obterQuantiaValorMonetário() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		 vmBRL = vmBRL.somar(dindin);
		 //Exercise SUT
		 Dinheiro quantia = vmBRL.obterQuantia();
		 //Result Verification
		 assertEquals(dindin, quantia);
		 //Fixture Teardown
	}

	@Test
	public void checarNegativoTrue() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		 vmBRL = vmBRL.subtrair(dindin);
		 //Exercise SUT
		 Boolean neg = vmBRL.negativo();
		 //Result Verification
		 assertEquals(true, neg );
		 //Fixture Teardown
	}
	
	@Test
	public void checarNegativoFalse() { //Implicit  //inline
		//Fixture Setup
		 Dinheiro dindin = new Dinheiro(Moeda.BRL, 100, 0);
		 vmBRL = vmBRL.somar(dindin);
		 //Exercise SUT
		 Boolean neg = vmBRL.negativo();
		 //Result Verification
		 assertEquals(false, neg );
		 //Fixture Teardown
	}
	
	@Test
	public void equalsValorMonetarioTrue() { //Implicit  //inline
		 //Fixture Setup
		 ValorMonetario outro = new ValorMonetario(Moeda.CHF);
		 //Exercise SUT
		 Boolean igual = vmCHF.equals(outro);
		 //Result Verification
		 assertEquals(true, igual);
		 //Fixture Teardown
	}
	
	@Test
	public void equalsDinheiroFalse() { //Implicit
		//Fixture Setup
		 //Exercise SUT
		 Boolean igual = vmCHF.equals(vmBRL);
		 //Result Verification
		 assertEquals(true, igual);
		 //Fixture Teardown
	}
	
	@Test
	public void equalsDinheiroDiferentesObjetos() { //Implicit
		 //Fixture Setup
		 //Exercise SUT
		 Boolean igual = vmCHF.equals(Moeda.BRL);
		 //Result Verification
		 assertEquals(false, igual);
		 //Fixture Teardown
	}
	
}
