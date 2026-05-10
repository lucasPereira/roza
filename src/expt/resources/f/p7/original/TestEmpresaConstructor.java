package tdd;

import static org.junit.Assert.*;

import org.junit.Test;

import tdd.Empresa;

public class TestEmpresaConstructor {

	@Test
	public void newEmpresa() throws Exception {
		// Fixture Setup
		Empresa minhaEmpresa = new Empresa();
		// Exercise SUT
		assertTrue(minhaEmpresa.getFuncionarios().isEmpty());
		assertTrue(minhaEmpresa.getProjetos().isEmpty());
	}
}
