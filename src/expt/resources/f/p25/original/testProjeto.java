package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GerenciadorDeOcorrencia.Empresa;
import GerenciadorDeOcorrencia.Funcionario;
import GerenciadorDeOcorrencia.PrioridadeOcorrencia;
import GerenciadorDeOcorrencia.Projeto;
import GerenciadorDeOcorrencia.TipoOcorrencia;

class testProjeto {
	
	Empresa empresa;
	Projeto projeto;
	Funcionario funcionario;
	
	@BeforeEach
	void configuracao() {
		empresa = new Empresa("Empresa Teste");
		projeto = empresa.iniciaProjeto("Projeto Teste");
		funcionario = empresa.contrataFuncionario();
	}

	@Test
	void projetoSemOcorrencias() {
		assertEquals(0, projeto.getOcorrencias().size());
	}
	
	@Test
	void projetoComUmaOcorrencia() {
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertEquals(1, projeto.getOcorrencias().size());
	}
	
	@Test
	void projetoComMultiplasOcorrencias() {
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.BUG, PrioridadeOcorrencia.MEDIA);
		assertEquals(2, projeto.getOcorrencias().size());
	}
}
