import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass25 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeProjeto;

	private String nomeFuncionario;

	private Projeto projeto;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
	}

	@Test()
	public void asMultiplasOcorrenciasDevemSerCriadasCorretamente() {
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		java.util.List<String> idsOcorrencias = java.util.List.of("#XY-1", "#XY-2");
		Iterator<Ocorrencia> itera = projeto.obterOcorrencias().iterator();
		assertEquals(0, projeto.obterOcorrencias().size());
		assertEquals(1, projeto.obterOcorrencias().size());
		assertEquals(2, projeto.obterOcorrencias().size());
		assertEquals(idsOcorrencias.get(0), itera.next().obterIdentificador());
		assertEquals(idsOcorrencias.get(1), itera.next().obterIdentificador());
	}

	@Test()
	public void asOcorrenciasDevemTerIdentificadoresUnicos() {
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		Set<Ocorrencia> setOcorrencias = new HashSet<Ocorrencia>(projeto.obterOcorrencias());
		assertEquals(0, projeto.obterOcorrencias().size());
		assertEquals(1, projeto.obterOcorrencias().size());
		assertEquals(2, projeto.obterOcorrencias().size());
		assertEquals(projeto.obterOcorrencias().size(), setOcorrencias.size());
	}

	@Test()
	public void naoPodeSerAlteradaAPrioridadeDeUmaOcorrenciaCompletada() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.completarOcorrencia();
		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());
	}

	@Test()
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaAberta() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaAlta() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.MEDIA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.ALTA);
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaBaixa() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.BAIXA);
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
		assertEquals(PRIORIDADE.BAIXA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void podeSerAlteradaAPrioridadeDeUmaOcorrenciaParaMedia() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.alterarPrioridade(PRIORIDADE.MEDIA);
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void umProjetoPodeCriarMultiplasOcorrencias() {
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(0, projeto.obterOcorrencias().size());
		assertEquals(1, projeto.obterOcorrencias().size());
		assertEquals(2, projeto.obterOcorrencias().size());
	}

	@Test()
	public void umProjetoPodeCriarSuaPrimeiraOcorrencia() {
		String resumo = "Testando a ocorrencia";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(0, projeto.obterOcorrencias().size());
		assertEquals(1, projeto.obterOcorrencias().size());
	}

	@Test()
	public void umaOcorrenciaAbertaPodeSerCompletada() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.completarOcorrencia();
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());
	}

	@Test()
	public void umaOcorrenciaCompletadaNaoDeveSerMaisAlterada() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.completarOcorrencia();
		primeiraOcorrencia.completarOcorrencia();
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveIniciarComEstadoAberta() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveIniciarComTipoBug() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(TIPO.BUG, primeiraOcorrencia.obterTipo());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveIniciarComTipoMelhoria() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.MELHORIA;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(TIPO.MELHORIA, primeiraOcorrencia.obterTipo());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveIniciarComTipoTarefa() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.TAREFA;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(TIPO.TAREFA, primeiraOcorrencia.obterTipo());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveTerUmIdentificador() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals("1", primeiraOcorrencia.obterIdentificador());
	}

	@Test()
	public void umaOcorrenciaCriadaDeveTerUmResumo() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(resumo, primeiraOcorrencia.obterResumo());
	}

	@Test()
	public void umaOcorrenciaCriadaNaoDeveSerCriadaComTipoNulo() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = null;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void umaOcorrenciaDeveAdicionarOFuncionarioResponsavel() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(nomeFuncionario, primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
	}

	@Test()
	public void umaOcorrenciaDeveSerCriadaComPrioridadeAlta() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void umaOcorrenciaDeveSerCriadaComPrioridadeBaixa() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.BAIXA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(PRIORIDADE.BAIXA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void umaOcorrenciaDeveSerCriadaComPrioridadeMedia() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.MEDIA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		assertEquals(PRIORIDADE.MEDIA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void umaOcorrenciaNaoDeveSerCriadaComPrioridadeNula() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = null;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmaPrioridadeNulo() {
		String id = "1";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Ocorrencia primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
		primeiraOcorrencia.alterarPrioridade(null);
		assertEquals(PRIORIDADE.ALTA, primeiraOcorrencia.obterPrioridade());
	}

	@Test()
	public void umaOcorrenciaNaoPodeTerUmIdentificadorNulo() {
		String id = null;
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void umaOcorrenciaNaoPodeTerUmIdentificadorVazio() {
		String id = "";
		String resumo = "Falha no servi�o X.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void umaOcorrenciaNaoPodeTerUmResumoNulo() {
		String id = "1";
		String resumo = null;
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void umaOcorrenciaNaoPodeTerUmResumoVazio() {
		String id = "1";
		String resumo = "";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		new Ocorrencia(id, resumo, tipo, prioridade, nomeFuncionario);
	}
}
