package testes;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;

public class TesteDinheiro {

	private Dinheiro reais100Centavos20;
	
	@Before // Implicit Setup
	public void setup() {
		reais100Centavos20 =  new Dinheiro(Moeda.BRL, 100, 20);
	}
	
	@Test 	// In-line Setup
	public void testeDinheiroObterQuantiaEmEscala() {
		//	Fixture Setup
		
		//	Exercise SUT
		Dinheiro reais100centavos20 = new Dinheiro(Moeda.BRL, 100, 20);
		
		//	Result Verification
		assertEquals(10020, reais100centavos20.obterQuantiaEmEscala().intValue());
		
		// 	Fixture Teardown
	}
	
	@Test 	// Implicit Setup
	public void testeDinheiroObterMoeda() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals(Moeda.BRL, reais100Centavos20.obterMoeda());
		
		// 	Fixture Teardown
	}
	
	@Test	// Delegated Setup
	public void testeDinheiroFormatado() {
		assertEquals("100,20 BRL", TestHelper.fazDinheiroBRL100Inteiro20Fracionado().formatado());
	}

	@Test 	// In-line setup
	public void testeDinheiroPositivo() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("+100,20 BRL", reais100Centavos20.positivo().toString());
		
		// 	Fixture Teardown
	}
	
	@Test
	public void testeDinheiroNegativo() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("-100,20 BRL", reais100Centavos20.negativo().toString());
		
		// 	Fixture Teardown		
	}

	@Test
	public void testeDinheiroEquals() {
		//	Fixture Setup
		
		//	Exercise SUT
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 100, 20);
		
		//	Result Verification
		assertTrue(reais100Centavos20.equals(dinheiro));
		
		// 	Fixture Teardown
	}

	
	@Test
	public void testeDinheiroToString() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("100,20 BRL", reais100Centavos20.toString());
		
		// 	Fixture Teardown
	}
}
