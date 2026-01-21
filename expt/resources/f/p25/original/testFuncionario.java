package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GerenciadorDeOcorrencia.Empresa;
import GerenciadorDeOcorrencia.Funcionario;
import GerenciadorDeOcorrencia.PrioridadeOcorrencia;
import GerenciadorDeOcorrencia.Projeto;
import GerenciadorDeOcorrencia.TipoOcorrencia;

class testFuncionario {
	
	Empresa empresa;
	Projeto projeto;
	Funcionario funcionario;
	
	void criaOcorrenciasProjeto(Projeto projeto, Funcionario funcionario, int numOcorrencias) {
		for(int i = 0; i < numOcorrencias; i++)
			projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
	}
	
	@BeforeEach
	void configuracao() {
		empresa = new Empresa("Empresa Teste");
		projeto = empresa.iniciaProjeto("Projeto Teste");
		funcionario = empresa.contrataFuncionario();
	}
	
	@Test
	void funcionarioSemOcorrencias() {
		assertEquals(0, funcionario.getOcorrencias().size());
	}

	@Test
	void funcionarioCom1_Ocorrencias() {
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertEquals(1, funcionario.getOcorrencias().size());
	}

	@Test
	void funcionarioCom10Ocorrencias() {
		criaOcorrenciasProjeto(projeto, funcionario, 10);
		assertEquals(10, funcionario.getOcorrencias().size());
	}
	
	@Test
	void funcionarioNaoPodeTerMaisDe10Ocorrencias() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			criaOcorrenciasProjeto(projeto, funcionario, 11);
		}, "Um funcionário não pode ser responsável por mais de 10 ocorrencias");
	}
	
	@Test
	void funcionarioComOcorrenciasDeDiferentesProjetos() {
		Projeto novoProjeto = empresa.iniciaProjeto("Projeto Teste 2");
		
		projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		novoProjeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);

		assertEquals(2, funcionario.getOcorrencias().size());
	}
}
