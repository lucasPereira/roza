package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercicioTdd.Empresa;
import exercicioTdd.Funcionario;
import exercicioTdd.Ocorrencia;
import exercicioTdd.Prioridade;
import exercicioTdd.Projeto;
import exercicioTdd.TipoDeOcorrencia;

class TesteProjeto {
	
	public Empresa empresa;
	public Funcionario funcionario;
	public Ocorrencia ocorrencia;
	

	@BeforeEach
	void setUp() throws Exception {
		empresa = new Empresa("Empresa Grande");
		empresa.contratarFuncionario("Maria");
		funcionario = empresa.pegarFuncionario(0);
	}

	@Test
	void criarProjetoSemCodigo() throws Exception {
		empresa.adicionarProjeto("");
		assertEquals(0, empresa.pegarNumeroDeProjetos());
	}
	
	@Test
	void criarProjetoComCodigo() throws Exception {
		empresa.adicionarProjeto("IMW");
		assertEquals("IMW", empresa.pegarProjeto(0).pegarCodigoProjeto());
	}
	
	@Test
	void adicionarUmFuncionario() throws Exception {
		empresa.adicionarProjeto("IMW");
		Projeto projetoImw = empresa.pegarProjeto(0);
		projetoImw.adicionarFuncionario(funcionario);
		assertEquals(1, projetoImw.pegarNumeroDeFuncionarios());
	}
//	
	@Test
	void adicionarDoisFuncionarios() throws Exception {
		empresa.contratarFuncionario("mariana");
		Funcionario mariana = empresa.pegarFuncionario(1);
		
		empresa.adicionarProjeto("IMW");
		Projeto projetoImw = empresa.pegarProjeto(0);
		projetoImw.adicionarFuncionario(funcionario);
		projetoImw.adicionarFuncionario(mariana);
		
		assertEquals(2, projetoImw.pegarNumeroDeFuncionarios());
	}
	
	@Test
	void criarUmaOcorrencia() throws Exception {
		empresa.adicionarProjeto("IMW");
		Projeto projetoImw = empresa.pegarProjeto(0);
		projetoImw.adicionarFuncionario(funcionario);
		projetoImw.criarOcorrencia(funcionario,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		assertEquals(1, projetoImw.pegarNumeroDeOcorrencias());
		assertEquals(1, funcionario.pegarNumeroOcorrencias());
	}
}
