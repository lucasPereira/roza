package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import GerenciadorDeOcorrencia.Empresa;
import GerenciadorDeOcorrencia.Funcionario;
import GerenciadorDeOcorrencia.Ocorrencia;
import GerenciadorDeOcorrencia.PrioridadeOcorrencia;
import GerenciadorDeOcorrencia.Projeto;
import GerenciadorDeOcorrencia.TipoOcorrencia;
import exceptions.MudarOcorrenciaFechadaException;

class testOcorrencia {
	
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
	void testaChaveUnicaOcorrencia() {
		Ocorrencia novaOcorrencia = projeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertNotEquals(ocorrencia.getChave(), novaOcorrencia.getChave());
	}
	
	@Test
	void ocoreenciasEmProjetosDiferentes()  {
		Projeto novoProjeto = empresa.iniciaProjeto("Projeto Teste");
		Ocorrencia novaOcorrencia = novoProjeto.criaOcorrencia("Ocorrencia Para Teste", funcionario, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
		assertEquals(ocorrencia.getChave(), novaOcorrencia.getChave());
	}
	
	@Test
	void testaFuncionarioNulo() {
		assertThrows(NullPointerException.class, () -> {
			projeto.criaOcorrencia("Ocorrencia Para Teste", null, TipoOcorrencia.MELHORIA, PrioridadeOcorrencia.ALTA);
        }, "Uma ocorrencia fechada não pode receber funcionário nulo");
	}
	
	@Test
	void naoModificaPrioridadeOcorrencia() {
		assertEquals(PrioridadeOcorrencia.ALTA, ocorrencia.getPrioridade());
	}
	
	@Test
	void modificaPrioridadeOcorrenciaBaixa() throws MudarOcorrenciaFechadaException {
		ocorrencia.setPrioridade(PrioridadeOcorrencia.BAIXA);
		assertEquals(PrioridadeOcorrencia.BAIXA, ocorrencia.getPrioridade());
	}
	
	@Test
	void modificaPrioridadeOcorrenciaMedia() throws MudarOcorrenciaFechadaException {
		ocorrencia.setPrioridade(PrioridadeOcorrencia.MEDIA);
		assertEquals(PrioridadeOcorrencia.MEDIA, ocorrencia.getPrioridade());
	}
	
	@Test
	void ocorrenciaComResponsavelInicial() {
		assertEquals(funcionario, ocorrencia.getFuncionarioResponsavel());
	}

}
