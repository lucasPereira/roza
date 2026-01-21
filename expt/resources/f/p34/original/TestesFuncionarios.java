package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

class TestesFuncionarios {
	
	public Funcionario funcionarioLuiz;
	public Empresa empresaBrasil;
	
	@BeforeEach
	void setarFunctionario() {
		empresaBrasil = new Empresa("Empresa Brasil");
		funcionarioLuiz = new Funcionario("Luiz Fernando");
	}

	@Test // 3
	void criarFuncionarioSemNome() throws Exception {
		Funcionario funcionarioSemNome = new Funcionario("");
		assertEquals("", funcionarioSemNome.getNome());
	}
	
	@Test // 4
	void criarFuncionarioComNome() throws Exception {
		String nomeFuncionario = "Luiz Fernando";
		assertEquals(nomeFuncionario, funcionarioLuiz.getNome());
	}
}
