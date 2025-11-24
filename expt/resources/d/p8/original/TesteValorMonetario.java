package sistemaBancario;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import sistemaBancario.Dinheiro;
import sistemaBancario.Moeda;
import sistemaBancario.ValorMonetario;

public class TesteValorMonetario {
	
	Dinheiro VinteReais;
	ValorMonetario valorMonetario;
	
	//implicit
	@Before
	public void criarValorMonetario(){
		VinteReais =  new Dinheiro(Moeda.BRL, 20, 00);
		valorMonetario = new ValorMonetario(Moeda.BRL);	
	}
	

	@Test
	public void somarValorMonetario (){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valor = valorMonetario.somar(VinteReais);	
		//Result Verification
		assertEquals("+20,00 BRL", valor.formatado());
		//Fixture Teardown
	}
	
	
	@Test
	public void subtrairValorMonetario (){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valor = valorMonetario.subtrair(VinteReais);	
		//Result Verification
		assertEquals("-20,00 BRL", valor.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void obterQuantiaValorMonetario (){
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valor = valorMonetario.somar(VinteReais);	
		//Result Verification
		assertEquals("20,00 BRL", valor.obterQuantia().formatado());
		//Fixture Teardown
	}
	
	
}
