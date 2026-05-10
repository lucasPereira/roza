import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeFuncionario;

	private String nomeProjeto;

	private Projeto projeto;

	private Funcionario funcionario;

	private Integer contadorOcorrencias;

	private String resumo;

	private TIPO tipo;

	private PRIORIDADE prioridade;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomeFuncionario);
		funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		resumo = "Falha no servi�o X.";
		tipo = TIPO.BUG;
		prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
	}

	@Test()
	public void umFuncionarioTemMultiplasOcorrencias() {
		resumo = "Falha no servi�o Y.";
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
		assertThat(2, is(contadorOcorrencias));
	}

	@Test()
	public void umFuncionarioTemMultiplasOcorrenciasCorretamente() {
		resumo = "Falha no servi�o Y.";
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		String resumoOcorrenciaUm = projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo();
		String resumoOcorrenciaDois = projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterResumo();
		Funcionario funcRespOcorrenciaUm = projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterFuncionarioResponsavel();
		Funcionario funcRespOcorrenciaDois = projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterFuncionarioResponsavel();
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
		assertThat(2, is(contadorOcorrencias));
		assertEquals("Falha no servi�o X.", resumoOcorrenciaUm);
		assertEquals("Falha no servi�o Y.", resumoOcorrenciaDois);
		assertEquals(funcionario, funcRespOcorrenciaUm);
		assertEquals(funcionario, funcRespOcorrenciaDois);
	}

	@Test()
	public void umFuncionarioTemUmaOcorrencia() {
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
	}
}
