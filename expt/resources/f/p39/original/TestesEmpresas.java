package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercicioTdd.Empresa;
import exercicioTdd.Funcionario;
import exercicioTdd.Prioridade;
import exercicioTdd.Projeto;
import exercicioTdd.TipoDeOcorrencia;

class TestesEmpresas {
	
	public Empresa empresaX;
	public Empresa empresaY;
	public Funcionario funcionario;

	@BeforeEach
	void iniciarTestes() {
		empresaY = new Empresa("Empresa grande");
		empresaY.contratarFuncionario("José");
	}
	
	@Test
	void testarCriacaoEmpresaSemNome() throws Exception {
		String nomeEmpresa = "";
		
		empresaX = new Empresa(nomeEmpresa);
		
		assertEquals("Empresa sem nome", empresaX.pegarNomeDaEmpresa());
	}

	@Test
	void testarCriacaoEmpresa() throws Exception {
		String nomeEmpresa = "Empresa Grande";
		
		empresaX = new Empresa(nomeEmpresa);
		
		assertEquals(nomeEmpresa, empresaX.pegarNomeDaEmpresa());
		assertEquals(0, empresaX.pegarNumeroDeFuncionarios());
	}
	
	@Test
	void contratarFuncionarioVazio() throws Exception {
		empresaY.contratarFuncionario("");
		
		assertEquals(1, empresaY.pegarNumeroDeFuncionarios());
	}
	
	@Test
	void contratarFuncionarioJose() throws Exception {
		String nomeFuncionario = "José";
		
		empresaY.contratarFuncionario(nomeFuncionario);
		
		assertEquals(2, empresaY.pegarNumeroDeFuncionarios());
	}
	
	@Test
	void criarProjeto() throws Exception {
		empresaY.adicionarProjeto("");
		
		assertEquals(1, empresaY.pegarNumeroDeProjetos());
	}
	
	@Test
	void criarDoisProjetos() throws Exception {
		String codigoProjeto = "IKF";
		empresaY.adicionarProjeto(codigoProjeto);

		empresaY.adicionarProjeto("IMW-1");
		
		assertEquals(2, empresaY.pegarNumeroDeProjetos());
	}
}
