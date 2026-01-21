package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercicioTdd.Empresa;
import exercicioTdd.Funcionario;

class TestesFuncionarios {
	
	public Funcionario funcionario;
	public Empresa empresa;
	
	@BeforeEach
	void setarFunctionario() {
		empresa = new Empresa("Empresa grande");
		funcionario = new Funcionario("José");
	}

	@Test
	void criarFuncionarioSemNome() throws Exception {
		Funcionario funcionarioVazio = new Funcionario("");
		
		assertEquals("", funcionarioVazio.pegarNome());
	}
	
	@Test
	void criarFuncionarioComNome() throws Exception {
		String nomeFuncionario = "José";
		
		assertEquals(nomeFuncionario, funcionario.pegarNome());
	}
}
