import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Ocorrencia ocurencia;

	@Before()
	public void setup() {
		this.joao = new Funcionario();
		ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", joao);
	}

	@Test()
	public void criarOcurrencia() {
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());
	}

	@Test()
	public void mudarPrioridade() {
		ocurencia.setPrioridade(Prioridades.MEDIA);
		ocurencia.setPrioridade(Prioridades.BAIXA);
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());
		assertEquals(Prioridades.MEDIA, ocurencia.getPrioridade());
		assertEquals(Prioridades.BAIXA, ocurencia.getPrioridade());
	}

	@Test()
	public void recuperarChaveDaOcurrencia() {
		long chave = ocurencia.getChave();
		assertNotEquals(null, chave);
	}

	@Test()
	public void recuperarFuncionarioOcurrencia() {
		Funcionario joao2 = ocurencia.getResponsavel();
		assertEquals(joao, joao2);
	}

	@Test()
	public void recuperarResumoOcurrencia() {
		String resume = ocurencia.getResume();
		assertEquals(resume, "resumo");
	}

	@Test()
	public void terminarOcorrencia() {
		ocurencia.fechar();
		assertEquals(true, ocurencia.getEstado());
		assertEquals(false, ocurencia.getEstado());
	}

	@Test()
	public void verificarOcorrenciaUnica() {
		assertTrue(new Ocorrencia(Prioridades.ALTA, "resumo", joao).getChave() >= ocurencia.getChave());
	}
}
