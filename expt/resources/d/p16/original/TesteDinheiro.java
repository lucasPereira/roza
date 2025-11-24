package teste.br.ufsc.ine.leb.sistemaBancario;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteDinheiro {
	
	private Dinheiro quantia;
	
	@Before
	public void setUp() {
		Integer inteiro = 100;
		Integer fracionado = 50;
		quantia = new Dinheiro(Moeda.BRL, inteiro, fracionado);
	}
	
	@Test
	public void dinheiroFormatado() {
		//Fixture Setup
		//Exercise SUT
		String dinheiroFormatado = quantia.formatado();
		//Result Verification
		assertEquals("100,50 BRL", dinheiroFormatado);
		//Fixture Teardown
		
	}
	
	@Test
	public void valorMonetarioPositivo() {
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valorPositivo = quantia.positivo();
		//Result Verification
		assertEquals("+100,50 BRL", valorPositivo.formatado());
		//Fixture Teardown
	}
	
	@Test
	public void valorMonetarioNegativo() {
		//Fixture Setup
		//Exercise SUT
		ValorMonetario valorNegativo = quantia.negativo();
		//Result Verification
		assertEquals("-100,50 BRL", valorNegativo.formatado());
		//Fixture Teardown
	}
}
