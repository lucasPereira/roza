import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass19 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeFuncionario;

	private String nomeProjetoUm;

	private String nomeProjetoDois;

	private Projeto projetoUm;

	private Projeto projetoDois;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		nomeProjetoUm = "XY";
		nomeProjetoDois = "YZ";
		empresa.adicionarProjeto(nomeProjetoUm);
		empresa.adicionarProjeto(nomeProjetoDois);
		projetoUm = empresa.obterProjetoPeloNome(nomeProjetoUm);
		projetoDois = empresa.obterProjetoPeloNome(nomeProjetoDois);
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);
		funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);
	}

	@Test()
	public void umFuncionarioDeveReduzirSuaContagemDeOcorrenciasAbertasAoCompletarOcorrencia() {
		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		Ocorrencia ocorrenciaParaCompletar = projetoUm.obterOcorrenciaPeloIdentificador("#XY-2");
		ocorrenciaParaCompletar.completarOcorrencia();
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
		assertThat(4, is(contadorOcorrencias));
		assertThat(3, is(contadorOcorrencias));
	}

	@Test()
	public void umFuncionarioNaoDeveSerAlocadoEmMaisDeDEZOcorrenciasAbertas() {
		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		assertThat(0, is(contadorOcorrencias));
		assertThat(10, is(contadorOcorrencias));
	}

	@Test()
	public void umFuncionarioPodeSerAlocadoEmAteDezOcorrenciasAbertas() {
		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
		assertThat(10, is(contadorOcorrencias));
	}

	@Test()
	public void umFuncionarioPodeSerAlocadoEmOcorrenciasDeProjetosDiferentes() {
		Integer contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		String resumoUm = "Falha no servi�o X do projeto Um.";
		String resumoDois = "Falha no servi�o Y do projeto Dois.";
		TIPO tipo = TIPO.BUG;
		PRIORIDADE prioridade = PRIORIDADE.ALTA;
		projetoUm.adicionarOcorrencia(resumoUm, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projetoDois.adicionarOcorrencia(resumoDois, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		String resumoOcorrenciaUmProjetoUm = projetoUm.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo();
		String resumoOcorrenciaUmProjetoDois = projetoDois.obterOcorrenciaPeloIdentificador("#YZ-1").obterResumo();
		Funcionario funcRespOcorrenciaUmProjetoUm = projetoUm.obterOcorrenciaPeloIdentificador("#XY-1").obterFuncionarioResponsavel();
		Funcionario funcRespOcorrenciaUmProjetoDois = projetoDois.obterOcorrenciaPeloIdentificador("#YZ-1").obterFuncionarioResponsavel();
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
		assertThat(2, is(contadorOcorrencias));
		assertEquals(resumoUm, resumoOcorrenciaUmProjetoUm);
		assertEquals(resumoDois, resumoOcorrenciaUmProjetoDois);
		assertEquals(funcionario, funcRespOcorrenciaUmProjetoUm);
		assertEquals(funcionario, funcRespOcorrenciaUmProjetoDois);
	}
}
