package testes;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;
import br.ufsc.ine.leb.sistemaBancario.Dinheiro;;

public class DinheiroTest {

	
	@Test
	public void formatadoComMoeda(){
		//Fixture Setup
		Dinheiro dinheiroCemReais = TestHelper.criarDinheiroCemReais(); 
		//Exercise SUT
		String formatado = dinheiroCemReais.formatado();
		//Result Verification
		assertEquals("100,00 BRL", formatado);
		//Fixture Teardown
	}
	
	@Test
	public void formatadoSemMoeda(){
		//Fixture Setup
		Dinheiro dinheiroZerado = new Dinheiro(Moeda.BRL, 0, 00); 
		//Exercise SUT
		String formatado = dinheiroZerado.formatado();
		//Result Verification
		assertEquals("0,00", formatado);
		//Fixture Teardown
	}
	
	@Test
	public void criaDinheiroComMoedaBRL(){
		//Fixture Setup
		//Exercise SUT
		Dinheiro dinheiroCemReais = TestHelper.criarDinheiroCemReais();
		//Result Verification
		assertEquals("100,00 BRL", dinheiroCemReais.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void criaDinheiroComMoedaUSD(){
		//Fixture Setup
		//Exercise SUT
		Dinheiro dinheiroCemDolares = TestHelper.criarDinheiroCemReais(); 
		//Result Verification
		assertEquals("100,00 USD", dinheiroCemDolares.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void criaDinheiroComMoedaCHF(){
		//Fixture Setup
		//Exercise SUT
		Dinheiro dinheiroCemFrancos = new Dinheiro(Moeda.CHF, 100, 00); 
		//Result Verification
		assertEquals("100,00 CHF", dinheiroCemFrancos.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void obterQuantiaEmEscala(){
		//Fixture Setup
		Dinheiro dinheiroCemReais = TestHelper.criarDinheiroCemReais();
		//Exercise SUT
		Integer quantia = dinheiroCemReais.obterQuantiaEmEscala();
		//Result Verification
		assertEquals(10000, quantia.intValue());
		//Fixture Teardown
	}
	
	@Test
	public void valorMonetarioPositivo(){
		//Fixture Setup
		Dinheiro dinheiroCemReais = new Dinheiro(Moeda.BRL, -100, 00);
		//Exercise SUT
		ValorMonetario valorMonetario = dinheiroCemReais.positivo();
		//Result Verification
		assertEquals("+100,00 BRL", valorMonetario.toString());
		//Fixture Teardown
	}
	
	@Test
	public void valorMonetarioNegativo(){
		//Fixture Setup
		Dinheiro dinheiroCemReais = TestHelper.criarDinheiroCemReais();
		//Exercise SUT
		ValorMonetario valorMonetario = dinheiroCemReais.negativo();
		//Result Verification
		assertEquals("-100,00 BRL", valorMonetario.toString());
		//Fixture Teardown
	}
}
