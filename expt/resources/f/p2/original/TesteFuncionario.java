package test.br.ufsc.testes.exercicio_tdd;

import static org.junit.Assert.*;

import org.junit.Test;

import modelo.br.ufsc.testes.exercicio_tdd.Funcionario;

public class TesteFuncionario {
	
	@Test
	public void testaFuncionariosDiferentesComNomesIguais() {
		Funcionario joao = new Funcionario("João");
		Funcionario joao2 = new Funcionario("João");
		assertFalse(joao.equals(joao2));
	}
	
}
