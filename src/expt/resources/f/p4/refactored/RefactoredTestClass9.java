import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Funcionario joao;

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		joao = new Funcionario();
		ocorrencia = new Ocorrencia(joao);
	}

	@Test()
	public void alteraPrioridadeComOcorrenciaCompletada() {
		ocorrencia.alterarPrioridade(Prioridades.BAIXA);
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		ocorrencia.alterarPrioridade(Prioridades.ALTA);
	}

	@Test()
	public void chaveSequencialDuasOcorrencias() {
		Ocorrencia outraOcorrencia = new Ocorrencia(joao);
		assertNotEquals(ocorrencia.obtemChave(), outraOcorrencia.obtemChave());
	}

	@Test()
	public void chaveUnicaDuasOcorrencias() {
		Ocorrencia outraOcorrencia = new Ocorrencia(joao);
		assertTrue(ocorrencia.obtemChave() < outraOcorrencia.obtemChave());
	}

	@Test()
	public void defineResumo() {
		ocorrencia.defineResumo("Este � o resumo da primeira ocorr�ncia.");
		assertEquals("Este � o resumo da primeira ocorr�ncia.", ocorrencia.obterResumo());
	}

	@Test()
	public void estadoAlteradoCOMLPETADA() {
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		assertEquals(Estados.COMPLETADA, ocorrencia.obtemEstado());
	}

	@Test()
	public void estadoAlteradoCOMLPETADADepoisABERTA() {
		ocorrencia.alterarEstado(Estados.COMPLETADA);
		ocorrencia.alterarEstado(Estados.ABERTA);
		assertEquals(Estados.ABERTA, ocorrencia.obtemEstado());
	}

	@Test()
	public void estadoInicialABERTA() {
		assertEquals(Estados.ABERTA, ocorrencia.obtemEstado());
	}

	@Test()
	public void joaoResponsavel() {
		assertEquals(joao.obtemChave(), ocorrencia.obtemResponsavel().obtemChave());
	}

	@Test()
	public void prioridadeAlta() {
		ocorrencia.alterarPrioridade(Prioridades.ALTA);
		assertEquals(Prioridades.ALTA, ocorrencia.obterPrioridade());
	}

	@Test()
	public void prioridadeBaixa() {
		ocorrencia.alterarPrioridade(Prioridades.BAIXA);
		assertEquals(Prioridades.BAIXA, ocorrencia.obterPrioridade());
	}

	@Test()
	public void prioridadeMedia() {
		ocorrencia.alterarPrioridade(Prioridades.MEDIA);
		assertEquals(Prioridades.MEDIA, ocorrencia.obterPrioridade());
	}

	@Test()
	public void tipoBug() {
		ocorrencia.alterarTipo(Tipos.BUG);
		assertEquals(Tipos.BUG, ocorrencia.obterTipo());
	}

	@Test()
	public void tipoMelhoria() {
		ocorrencia.alterarTipo(Tipos.MELHORIA);
		assertEquals(Tipos.MELHORIA, ocorrencia.obterTipo());
	}

	@Test()
	public void tipoTarefa() {
		ocorrencia.alterarTipo(Tipos.TAREFA);
		assertEquals(Tipos.TAREFA, ocorrencia.obterTipo());
	}
}
