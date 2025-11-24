package teste.br.ufsc.ine.leb.sistemaBancario.dinheiro;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;
import teste.br.ufsc.ine.leb.sistemaBancario.helpers.TesteHelper;

public class TesteDinheiro {
	
	private Integer inteiro;
	private Integer fracionado;
	private Dinheiro dinheiro;

	@Before
	public void setUp() {
		inteiro = new Integer(500);
		fracionado = new Integer(0);
		dinheiro = new Dinheiro(Moeda.BRL, inteiro, fracionado);
	}
	
	@Test
	public void criaDinheiro() {
		// Fixture Setup - Inline
		Integer inteiro = new Integer(500);
		Integer fracionado = new Integer(25);
		Moeda moeda = Moeda.BRL;
		
		// Exercise SUT
		Dinheiro dinheiro = new Dinheiro(moeda, inteiro, fracionado);
		
		// Result Verification
		assertEquals(TesteHelper.quantiaFormatadoComMoeda(inteiro, fracionado, moeda), dinheiro.formatado());
		
		// Fixture Teardown
	}
	
	@Test
	public void obterTipoDeMoedaDoDinheiro_DeveSerBRL() {
		// Fixture Setup - Implicit
		
		// Exercise SUT
		Moeda tipoDaMoeda = dinheiro.obterMoeda();
		
		// Result Verification
		assertEquals(Moeda.BRL, tipoDaMoeda);
		
		// Fixture Teardown
	}
	
	@Test
	public void pegaValorPositivoDoDinheiro() {
		// Fixture Setup - Implicit Setup
		
		// Exercise SUT
		ValorMonetario positivo = dinheiro.positivo();
		
		// Result Verification
		assertTrue(new Boolean(!positivo.negativo()));
		
		// Fixture Teardown
	}
	
	@Test
	public void pegaValorNegativoDoDinheiro() {
		// Fixture Setup - Implicit Setup
		
		// Exercise SUT
		ValorMonetario negativo = dinheiro.negativo();
		
		// Result Verification
		assertTrue(new Boolean(negativo.negativo()));
		
		// Fixture Teardown
	}
	
	@Test
	public void criaDinheiroZero2() {
		// Fixture Setup - Implicit Setup
		
		// Exercise SUT
		
		// Result Verification
		
		// Fixture Teardown
	}
}
