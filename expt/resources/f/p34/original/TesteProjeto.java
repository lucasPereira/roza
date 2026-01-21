package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

class TesteProjeto {
	
	public Empresa empresaBrasil;
	public Funcionario funcionarioLuiz;
	
	@BeforeEach
	void setUp() throws Exception {
		empresaBrasil = new Empresa("Empresa Brasil");
		empresaBrasil.contratarFuncionario("Luiz Fernando");
		funcionarioLuiz = empresaBrasil.getFuncionario(0);
	}

	@Test // 7
	void criarProjetoSemCodigo() throws Exception {
		empresaBrasil.adicionarProjeto("");
		assertEquals(0, empresaBrasil.getNumeroDeProjetos());
	}
	
	@Test // 8
	void criarProjetoComCodigo() throws Exception {
		empresaBrasil.adicionarProjeto("IMW"); 
		assertEquals(1, empresaBrasil.getNumeroDeProjetos());
		assertEquals("IMW", empresaBrasil.getProjeto(0).getCodigoProjeto());
	}
	
	@Test // 9
	void criarDoisProjetos() throws Exception {
		String codigoProjeto = "WPP";
		empresaBrasil.adicionarProjeto(codigoProjeto);
		codigoProjeto = "WPP2";
		empresaBrasil.adicionarProjeto(codigoProjeto);
		
		assertEquals(2, empresaBrasil.getNumeroDeProjetos());
	}
	
	@Test // 10
	void adicionarUmFuncionario() throws Exception {
		empresaBrasil.adicionarProjeto("IMW");
		Projeto projetoImw = empresaBrasil.getProjeto(0);
		projetoImw.adicionarFuncionario(funcionarioLuiz);
		assertEquals(1, projetoImw.getNumeroDeFuncionarios());
	}

	@Test // 11
	void adicionarDoisFuncionarios() throws Exception {
		empresaBrasil.contratarFuncionario("Paulo");
		Funcionario funcionarioPaulo = empresaBrasil.getFuncionario(1);
		
		empresaBrasil.adicionarProjeto("IMW");
		Projeto projetoImw = empresaBrasil.getProjeto(0);
		projetoImw.adicionarFuncionario(funcionarioLuiz);
		projetoImw.adicionarFuncionario(funcionarioPaulo);
		
		assertEquals(2, projetoImw.getNumeroDeFuncionarios());
	}
	
	@Test // 14
	void criarUmaOcorrencia() throws Exception {
		empresaBrasil.adicionarProjeto("IMW");
		Projeto projetoImw = empresaBrasil.getProjeto(0);
		projetoImw.adicionarFuncionario(funcionarioLuiz);
		projetoImw.criarOcorrencia(funcionarioLuiz,TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar alguma coisa");
		assertEquals(1, projetoImw.getNumeroDeOcorrencias());
		assertEquals(1, funcionarioLuiz.getNumeroOcorrencias());
	}
}
