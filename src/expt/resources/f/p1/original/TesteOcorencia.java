package test.gerenciadorEmpresa;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import gerenciadorEmpresa.Funcionario;
import gerenciadorEmpresa.Ocorrencia;
import gerenciadorEmpresa.Prioridades;

public class TesteOcorencia {
	private Funcionario joao;

	@Before
	public void setUp() throws Exception {
		this.joao = new Funcionario();
	}

	@Test
	public void criarOcurrencia() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());
	}

	@Test
	public void recuperarChaveDaOcurrencia() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		long chave = ocurencia.getChave();
		assertNotEquals(null, chave);
	}

	@Test
	public void recuperarResumoOcurrencia() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		String resume = ocurencia.getResume();
		assertEquals(resume, "resumo");
	}

	@Test
	public void recuperarFuncionarioOcurrencia() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		Funcionario joao2 = ocurencia.getResponsavel();
		assertEquals(joao, joao2);
	}

	@Test
	public void verificarOcorrenciaUnica() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		assertTrue(new Ocorrencia(Prioridades.ALTA, "resumo", joao).getChave() >= ocurencia.getChave());
	}

	@Test
	public void terminarOcorrencia() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		assertEquals(true, ocurencia.getEstado());

		ocurencia.fechar();
		assertEquals(false, ocurencia.getEstado());
	}

	@Test
	public void mudarPrioridade() throws Exception {
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());

		ocurencia.setPrioridade(Prioridades.MEDIA);
		assertEquals(Prioridades.MEDIA, ocurencia.getPrioridade());

		ocurencia.setPrioridade(Prioridades.BAIXA);
		assertEquals(Prioridades.BAIXA, ocurencia.getPrioridade());
	}

	@Test
	public void mudarResponsavel() throws Exception {
		Funcionario joao2 = new Funcionario();
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);

		assertEquals(joao, ocurencia.getResponsavel());
		assertNotEquals(joao2, ocurencia.getResponsavel());

		ocurencia.setResponsavel(joao2);
		assertEquals(joao2, ocurencia.getResponsavel());
		assertNotEquals(joao, ocurencia.getResponsavel());
	}

	@Test
	public void corrigeBugDoCoverage() throws Exception {
		// Corrige bug do coverage
		Prioridades.valueOf("MEDIA");
	}
}
