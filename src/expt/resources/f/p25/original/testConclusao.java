package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GerenciadorDeOcorrencia.Empresa;
import GerenciadorDeOcorrencia.EstadoOcorrencia;
import GerenciadorDeOcorrencia.Funcionario;
import GerenciadorDeOcorrencia.Ocorrencia;
import GerenciadorDeOcorrencia.PrioridadeOcorrencia;
import GerenciadorDeOcorrencia.Projeto;
import GerenciadorDeOcorrencia.TipoOcorrencia;
import exceptions.MudarOcorrenciaFechadaException;

class testConclusao {
	
	Empresa empresa;
	Projeto projeto;
	Funcionario funcionario;
	Ocorrencia ocorrencia;
	
	@BeforeEach
	void configuracao() {
		empresa = new Empresa("Empresa Teste");
		projeto = empresa.iniciaProjeto("Projeto Teste");
		funcionario = empresa.contrataFuncionario();
		ocorrencia = projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
	}
	
	@Test
	void ocorrenciaEmAberto() {
		assertEquals(EstadoOcorrencia.ABERTA, ocorrencia.getEstado());
	}
	
	@Test
	void ocorrenciaFechada() {
		funcionario.terminaOcorrencia(ocorrencia);
		assertEquals(EstadoOcorrencia.FECHADA, ocorrencia.getEstado());
	}
	
	@Test
	void trocaResponsavelOcorrenciaAberta() throws MudarOcorrenciaFechadaException {
		Funcionario novoFuncionario = empresa.contrataFuncionario();
		ocorrencia.trocaFuncionarioResponsavel(novoFuncionario);
		assertEquals(novoFuncionario, ocorrencia.getFuncionarioResponsavel());
	}
	
	@Test
	void trocaResponsavelOcorrenciaFechada() {
		Funcionario novoFuncionario = empresa.contrataFuncionario();
		funcionario.terminaOcorrencia(ocorrencia);
		
		assertThrows(MudarOcorrenciaFechadaException.class, () -> {
			ocorrencia.trocaFuncionarioResponsavel(novoFuncionario);
        }, "Uma ocorrencia fechada não pode ser modificada");
	}
	
	@Test
	void trocaPrioridadelOcorrenciaAberta() throws MudarOcorrenciaFechadaException {
		ocorrencia.setPrioridade(PrioridadeOcorrencia.BAIXA);
		assertEquals(PrioridadeOcorrencia.BAIXA, ocorrencia.getPrioridade());
	}
	
	@Test
	void trocaPrioridadeOcorrenciaFechada() {
		funcionario.terminaOcorrencia(ocorrencia);		
		assertThrows(MudarOcorrenciaFechadaException.class, () -> {
			ocorrencia.setPrioridade(PrioridadeOcorrencia.BAIXA);
        }, "Uma ocorrencia fechada não pode ser modificada");
	}

}
