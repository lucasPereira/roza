package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Funcionario;
import model.Ocorrencia;
import model.Ocorrencia.Estado;
import model.Ocorrencia.Prioridade;
import model.Ocorrencia.Tipo;

public class TestOcorrencia {

	private Funcionario responsavelOcorrencia1;
	private Funcionario responsavelOcorrencia2;
	private Ocorrencia ocorrenciaId1;
	private Ocorrencia ocorrenciaId2;
	private Ocorrencia ocorrenciaId3;
	
	@Before
	public void setUp() {
		responsavelOcorrencia1 = new Funcionario(1, "Joao");
		responsavelOcorrencia2 = new Funcionario(2, "Maria");
		ocorrenciaId1 = new Ocorrencia(1, "Bug no botão 'sugestões'",
				Tipo.BUG, Prioridade.BAIXA, responsavelOcorrencia1);
		ocorrenciaId2 = new Ocorrencia(2, "Mudar cor de fundo da home",
				Tipo.MELHORIA, Prioridade.BAIXA, responsavelOcorrencia2);
		ocorrenciaId3 = new Ocorrencia(3, "Mudar formato do botão 'sugestão'",
				Tipo.MELHORIA, Prioridade.BAIXA, responsavelOcorrencia1);
	}
	
	@Test
	public void testCriarOcorrencia() {
		String resumo = "Aumentar quantidade de sugestões";
		Ocorrencia ocorrencia = new Ocorrencia(4, resumo,
				Tipo.TAREFA, Prioridade.MEDIA, responsavelOcorrencia1);
		
		assertEquals(resumo, ocorrencia.getResumo());
		assertEquals(Tipo.TAREFA, ocorrencia.getTipo());
		assertEquals(Prioridade.MEDIA, ocorrencia.getPrioridade());
		assertEquals(4, ocorrencia.getId());
		assertEquals(responsavelOcorrencia1, ocorrencia.getFuncionarioResponsavel());
		assertNotNull(ocorrencia.getFuncionarioResponsavel());
		assertEquals(Estado.ABERTA, ocorrencia.getEstado());
		assertEquals(3, responsavelOcorrencia1.getQuantidadeDeOcorrencias());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testCriarOcorrenciaFuncionarioNull() {
		new Ocorrencia(4, "Aumentar quantidade de sugestões", Tipo.TAREFA, Prioridade.MEDIA, null);
	}
	
	@Test
	public void testMudarPrioridadeDaOcorrencia1Valido() {
		ocorrenciaId1.setPrioridade(Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, ocorrenciaId1.getPrioridade());
	}
	
	@Test(expected=IllegalStateException.class)
	public void testMudarPrioridadeDaOcorrencia1Invalido() {
		ocorrenciaId1.setEstado(Estado.FINALIZADA);
		ocorrenciaId1.setPrioridade(Prioridade.ALTA);
	}

	@Test
	public void testFinalizarOcorrencia2Valido() {
		ocorrenciaId2.setEstado(Estado.FINALIZADA);
		assertEquals(Estado.FINALIZADA, ocorrenciaId2.getEstado());
	}
	
	@Test
	public void testAlterarResponsavelDaOcorrencia3Valido() {
		ocorrenciaId3.setFuncionarioResponsavel(responsavelOcorrencia2);
		assertEquals(responsavelOcorrencia2, ocorrenciaId3.getFuncionarioResponsavel());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAlterarResponsavelNullDaOcorrencia() {
		ocorrenciaId3.setFuncionarioResponsavel(null);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testAlterarResponsavelDaOcorrenciaInvalido() {
		ocorrenciaId3.setEstado(Estado.FINALIZADA);
		ocorrenciaId3.setFuncionarioResponsavel(responsavelOcorrencia1);
	}
	
	@Test
	public void testOcorrenciasIguais() {
		assertEquals(ocorrenciaId1, ocorrenciaId1);
	}
	
	@Test
	public void testOcorrenciasIguaisInstanciasDiferentes() {
		Ocorrencia ocorrenciaInstancia2 = new Ocorrencia(1, "Bug no botão 'sugestões'",
				Tipo.BUG, Prioridade.BAIXA, responsavelOcorrencia1);
		
		assertEquals(ocorrenciaId1, ocorrenciaInstancia2);
	}
	
	@Test
	public void testOcorrenciasDiferentes() {
		assertNotEquals(ocorrenciaId1, ocorrenciaId2);
	}
	
}
