import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Empresa empresa;

	private Projeto projeto;

	@Before()
	public void setup() {
		empresa = new Empresa("Gerenciador de Ocorrências");
		projeto = empresa.criarProjeto("TDD");
	}

	@Test()
	public void adicionarOcorrencia() {
		Ocorrencia novaOcorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.MELHORIA, Prioridade.ALTA);
		assertThat(projeto.getListaDeOcorrencias(), hasItem(novaOcorrencia));
		assertThat(projeto.getListaDeOcorrencias().size(), equalTo(1));
	}

	@Test()
	public void checarUnicidadeDaChaveDaOcorrência() {
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		Ocorrencia outraOcorrencia = projeto.criarOcorrencia("Ocorrência 02", "Descrição 02", Tipo.TAREFA, Prioridade.ALTA);
		assertThat(ocorrencia.getChave(), not(equalTo(outraOcorrencia.getChave())));
	}

	@Test()
	public void criarOcorrenciaComConstrutor() {
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		ocorrencia = new Ocorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		assertThat(ocorrencia.getTitulo(), equalTo("Ocorrência 01"));
		assertThat(ocorrencia.getResumo(), equalTo("Descrição 01"));
		assertThat(ocorrencia.getTipo(), equalTo(Tipo.TAREFA));
		assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.ALTA));
		assertThat(ocorrencia.getEstado(), equalTo(Estado.ABERTA));
		assertThat(ocorrencia.getResponsavel(), is(nullValue()));
	}

	@Test()
	public void criarProjetoComConstrutor() {
		projeto = new Projeto("TDD");
		assertThat(projeto.getNome(), equalTo("TDD"));
		assertThat(projeto.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(projeto.getListaDeFuncionarios().size(), equalTo(0));
	}

	@Test()
	public void existemTrêsTiposDeOcorrência() {
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		Ocorrencia ocorrenciaTipoBug = projeto.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.BUG, Prioridade.ALTA);
		Ocorrencia ocorrenciaTipoMelhoria = projeto.criarOcorrencia("Ocorrência 03", "Resumo 03", Tipo.MELHORIA, Prioridade.ALTA);
		assertThat(ocorrencia.getTipo(), equalTo(Tipo.TAREFA));
		assertThat(ocorrenciaTipoBug.getTipo(), equalTo(Tipo.BUG));
		assertThat(ocorrenciaTipoMelhoria.getTipo(), equalTo(Tipo.MELHORIA));
	}

	@Test()
	public void existemTrêsTiposDePrioridade() {
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		Ocorrencia ocorrenciaDePrioridadeMedia = projeto.criarOcorrencia("Ocorrência 02", "Resumo 02", Tipo.BUG, Prioridade.MEDIA);
		Ocorrencia ocorrenciaDePrioridadeBaixa = projeto.criarOcorrencia("Ocorrência 03", "Resumo 03", Tipo.MELHORIA, Prioridade.BAIXA);
		assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.ALTA));
		assertThat(ocorrenciaDePrioridadeMedia.getPrioridade(), equalTo(Prioridade.MEDIA));
		assertThat(ocorrenciaDePrioridadeBaixa.getPrioridade(), equalTo(Prioridade.BAIXA));
	}

	@Test()
	public void prioridadeDaOcorrenciaPodeSerModificada() {
		Ocorrencia ocorrencia = projeto.criarOcorrencia("Ocorrência 01", "Descrição 01", Tipo.TAREFA, Prioridade.ALTA);
		ocorrencia.setPrioridade(Prioridade.BAIXA);
		assertThat(ocorrencia.getPrioridade(), equalTo(Prioridade.BAIXA));
	}
}
