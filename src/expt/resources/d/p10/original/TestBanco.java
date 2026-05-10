package test;

import static org.junit.Assert.*;

import org.junit.Test;

import br.ufsc.ine.leb.sistemaBancario.Banco;
import br.ufsc.ine.leb.sistemaBancario.Moeda;
import br.ufsc.ine.leb.sistemaBancario.SistemaBancario;

public class TestBanco {

	/*
	 * Inline Setup
	 */
	@Test
	public void testCriarBanco() {
		// Fixture Setup: inline
		SistemaBancario sistemaBancario = new SistemaBancario();
		// Exercise SUT
		Banco bancoBradesco = sistemaBancario.criarBanco("Bradesco", Moeda.BRL);
		// Result verification
		assertEquals("Bradesco", bancoBradesco.obterNome());
		assertEquals(Moeda.BRL, bancoBradesco.obterMoeda());
		// Fixture Teardown
	}
}
