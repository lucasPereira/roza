package tdd;

import static org.junit.Assert.*;

import org.junit.Test;

import tdd.Funcionario;

public class TestFuncionarioConstructor {

	@Test
	public void newFuncionario() throws Exception {
		// Exercise SUT
		Funcionario meuFuncionario = new Funcionario();
		// Result Verification
		assertTrue(meuFuncionario.getProjetos().isEmpty());
		assertTrue(meuFuncionario.getOcorrenciasAbertas().isEmpty());
	}
}
