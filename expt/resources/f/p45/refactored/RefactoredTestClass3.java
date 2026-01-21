import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Funcionario responsavelJoao;

	private Funcionario responsavelMaria;

	private String resumoDaOcorrenciaTarefa;

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		responsavelJoao = new Funcionario("Joao");
		responsavelMaria = new Funcionario("Maria");
		resumoDaOcorrenciaTarefa = "Implementar nova funcionalidade";
		ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA, responsavelJoao);
	}

	@Test()
	public void alterarPrioridadeDeOcorrenciaAberta() {
		ocorrencia.alterarPrioridade(PrioridadeDeOcorrencia.ALTA);
		assertEquals(PrioridadeDeOcorrencia.ALTA, ocorrencia.obterPrioridade());
	}

	@Test()
	public void alterarPrioridadeDeOcorrenciaCompletada() {
		ocorrencia.terminar();
		ocorrencia.alterarPrioridade(PrioridadeDeOcorrencia.ALTA);
	}

	@Test()
	public void alterarResponsavelDeOcorrenciaAberta() {
		ocorrencia.alterarResponsavel(responsavelMaria);
		assertEquals(responsavelMaria, ocorrencia.obterResponsavel());
	}

	@Test()
	public void alterarResponsavelDeOcorrenciaCompletada() {
		ocorrencia.terminar();
		ocorrencia.alterarResponsavel(responsavelMaria);
	}

	@Test()
	public void criarOcorrencia() {
		assertEquals(EstadoDeOcorrencia.ABERTA, ocorrencia.obterEstado());
		assertEquals(Integer.valueOf(1), ocorrencia.obterChave());
		assertEquals(resumoDaOcorrenciaTarefa, ocorrencia.obterResumo());
		assertEquals(PrioridadeDeOcorrencia.MEDIA, ocorrencia.obterPrioridade());
		assertEquals(responsavelJoao, ocorrencia.obterResponsavel());
	}

	@Test()
	public void obterEstadoDeOcorrenciaCompletada() {
		ocorrencia.terminar();
		EstadoDeOcorrencia estado = ocorrencia.obterEstado();
		assertEquals(EstadoDeOcorrencia.COMPLETADA, estado);
	}
}
