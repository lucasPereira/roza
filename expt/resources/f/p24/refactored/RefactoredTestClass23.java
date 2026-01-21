import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass23 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeProjeto;

	private Projeto projeto;

	private String nomeFuncionario;

	private Funcionario funcionario;

	private Integer contadorOcorrencias;

	private String resumo;

	private TIPO tipo;

	private PRIORIDADE prioridade;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		projeto.adicionarFuncionario(nomeFuncionario);
		funcionario = empresa.obterFuncionarioPeloNome(nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		resumo = "Falha no servi�o X.";
		tipo = TIPO.BUG;
		prioridade = PRIORIDADE.ALTA;
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
	}

	@Test()
	public void aBuscaDaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorNulo() {
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projeto.obterOcorrenciaPeloIdentificador(null);
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
	}

	@Test()
	public void aBuscaDaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorVazio() {
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projeto.obterOcorrenciaPeloIdentificador("");
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
	}

	@Test()
	public void aBuscaDeUmaOcorrenciaPeloIdentificadorNaoPodeSerComIdentificadorInexistente() {
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		projeto.obterOcorrenciaPeloIdentificador("#ABC-142");
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
	}

	@Test()
	public void deveLocalizarEObterOcorrenciaPeloIdentificador() {
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
		assertThat(1, is(contadorOcorrencias));
		assertEquals(resumo, projeto.obterOcorrenciaPeloIdentificador("#XY-1").obterResumo());
	}

	@Test()
	public void deveLocalizarEObterOutraOcorrenciaPeloIdentificador() {
		projeto.adicionarOcorrencia(resumo, tipo, prioridade, nomeFuncionario);
		contadorOcorrencias = funcionario.obterContagemDeOcorrenciasAbertas();
		assertThat(0, is(contadorOcorrencias));
		assertThat(2, is(contadorOcorrencias));
		assertEquals(resumo, projeto.obterOcorrenciaPeloIdentificador("#XY-2").obterResumo());
	}
}
