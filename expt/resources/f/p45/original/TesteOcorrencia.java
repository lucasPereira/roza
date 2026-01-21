package gerenciadordeocorrencias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TesteOcorrencia {

	private Funcionario responsavelJoao;
	private Funcionario responsavelMaria;
	private String resumoDaOcorrenciaTarefa;

	@Before
	public void criarParametros() {
		responsavelJoao = new Funcionario("Joao");
		responsavelMaria = new Funcionario("Maria");
		resumoDaOcorrenciaTarefa = "Implementar nova funcionalidade";
	}

	@Test
	public void criarOcorrencia() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);

		assertEquals(EstadoDeOcorrencia.ABERTA, ocorrencia.obterEstado());
		assertEquals(Integer.valueOf(1), ocorrencia.obterChave());
		assertEquals(resumoDaOcorrenciaTarefa, ocorrencia.obterResumo());
		assertEquals(PrioridadeDeOcorrencia.MEDIA, ocorrencia.obterPrioridade());
		assertEquals(responsavelJoao, ocorrencia.obterResponsavel());
	}

	@Test
	public void obterEstadoDeOcorrenciaCompletada() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);
		ocorrencia.terminar();

		EstadoDeOcorrencia estado = ocorrencia.obterEstado();

		assertEquals(EstadoDeOcorrencia.COMPLETADA, estado);
	}

	@Test
	public void alterarPrioridadeDeOcorrenciaAberta() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);

		ocorrencia.alterarPrioridade(PrioridadeDeOcorrencia.ALTA);

		assertEquals(PrioridadeDeOcorrencia.ALTA, ocorrencia.obterPrioridade());
	}

	@Test(expected = Exception.class)
	public void alterarPrioridadeDeOcorrenciaCompletada() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);
		ocorrencia.terminar();

		ocorrencia.alterarPrioridade(PrioridadeDeOcorrencia.ALTA);
	}

	@Test
	public void alterarResponsavelDeOcorrenciaAberta() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);

		ocorrencia.alterarResponsavel(responsavelMaria);

		assertEquals(responsavelMaria, ocorrencia.obterResponsavel());
	}

	@Test(expected = Exception.class)
	public void alterarResponsavelDeOcorrenciaCompletada() {
		Ocorrencia ocorrencia = new Ocorrencia(1, resumoDaOcorrenciaTarefa, TipoDeOcorrencia.TAREFA,
				PrioridadeDeOcorrencia.MEDIA, responsavelJoao);
		ocorrencia.terminar();

		ocorrencia.alterarResponsavel(responsavelMaria);
	}

}
