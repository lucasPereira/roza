package test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Dinheiro;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.ValorMonetario;

public class TestValorMonetario {
	
	Dinheiro dezReais;
	ValorMonetario dezReaisVM;
	
	/*
	 * Implicit setup
	 */
	@Before
	public void setUp() {
		dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		dezReaisVM = new ValorMonetario(Moeda.BRL).somar(dezReais);
	}
	
	@Test
	public void testCriarValorMonetario() {
		// Fixture Setup
		// Exercise SUT
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		// Result Verification
		assertEquals(Moeda.BRL, vm.obterQuantia().obterMoeda());
		assertEquals(0, vm.obterQuantia().obterQuantiaEmEscala().intValue());
		// Fixture Teardown
	}

	@Test
	public void testSomarValorMonetario() {
		// Fixture Setup: implicit and inline
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Dinheiro somaEsperada = new Dinheiro(Moeda.BRL, 15, 0);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		
		// Exercise SUT
		ValorMonetario cincoReaisVM = vm.somar(cincoReais);
		ValorMonetario quinzeReaisVM = cincoReaisVM.somar(dezReais);
		
		// Result Verification
		assertEquals(somaEsperada, quinzeReaisVM.obterQuantia());

		//Fixture teardown
	}
	
	@Test
	public void testSubtrairValorMonetario() {
		// Fixture setup: implicit and inline
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);		
		// Exercise SUT
		ValorMonetario vm = valorMonetario.subtrair(dezReais);
		// Result verification
		assertEquals(dezReais.negativo().formatado(), vm.formatado());
		// Fixture teardown
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testSomaMoedasDiferentes() {
		// Fixture setup: implict and inline
		ValorMonetario vm = new ValorMonetario(Moeda.USD);
		
		//Exercise SUT
		vm.somar(dezReais);
		
		//Result verification
		//Fixture teardown
	}
	
	@Test
	public void testValoresMonetarioIguais() {
		// Fixture setup: implicit		
		//Exercise SUT
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(dezReais);

		//Result verification
		assertEquals(vm, dezReaisVM);
		
		//Fixture teardown
	}

}
