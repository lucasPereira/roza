package testes;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TesteBanco {
	
	private SistemaBancario sistemaBancario;
	private Banco banco;
	
	@Before	// Implicit Setup
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("bantest", Moeda.BRL);
		banco = sistemaBancario.obterBancos().get(0);
		
	}
	
	@Test	// Implicit Setup
	public void testeCriaBanco() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals(1, sistemaBancario.obterBancos().size());	
		
		// 	Fixture Teardown
	}
	
	@Test
	public void testeNomeBancoCorreto() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals("bantest", banco.obterNome());
		
		// 	Fixture Teardown		
	}
	
	@Test	// Implicit Setup
	public void testeMoedaBancoCorreta() {
		//	Fixture Setup
		
		//	Exercise SUT
		
		//	Result Verification
		assertEquals(Moeda.BRL, banco.obterMoeda());
		
		// 	Fixture Teardown
	}
}
