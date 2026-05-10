package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TesteValorMonetario {
	
	private ValorMonetario valorMonetario;
	private Dinheiro reais10Centavos20;
	
	@Before	// Implicit Setup
	public void setup() {
		//	Exercise SUT
		valorMonetario = new ValorMonetario(Moeda.BRL);
		reais10Centavos20 = new Dinheiro(Moeda.BRL, 10, 20);
	}
	
	@Test	// Implicit Setup e In-line Setup
	public void testeSomar() {		
		//	Fixture Setup
		valorMonetario = valorMonetario.somar(reais10Centavos20);
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("10,20 BRL", valorMonetario.obterQuantia().toString());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void testeSubtrair() {
		//	Fixture Setup
		valorMonetario = valorMonetario.subtrair(reais10Centavos20);
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("-10,20 BRL", valorMonetario.obterQuantia().negativo().toString());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeObterQuantia() { 
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertEquals("0,00", valorMonetario.obterQuantia().formatado());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeNegativo() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification			
		assertFalse(valorMonetario.negativo());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup
	public void testeFormatado() {
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertEquals("0,00", valorMonetario.formatado());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeFormatarComSinal() {
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertEquals("+0,00", valorMonetario.formatarComSinal());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeFormatarSemSinal() {
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertEquals("0,00", valorMonetario.formatarSemSinal());
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeZero() { 
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertTrue(valorMonetario.zero());
		
		// 	Fixture Teardown
	}
	
	@Test	// Implicit Setup e In-line Setup
	public void testeEquals() {		
		//	Fixture Setup

		//	Exercise SUT
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		
		//	Result Verification
		assertTrue(this.valorMonetario.equals(valorMonetario));
		
		// 	Fixture Teardown
	}

	@Test	// Implicit Setup
	public void testeValorMonetarioToString() {
		
		//	Fixture Setup
		
		//	Exercise SUT
			
		//	Result Verification
		assertEquals("0,00", valorMonetario.toString());
		
		// 	Fixture Teardown
	}
		
}