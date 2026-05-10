import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass21 {

	private String nomeEmpresa;

	private Empresa empresa;

	private String nomeProjeto;

	private java.util.List<String> nomesFuncs;

	private Projeto projeto;

	private String id;

	private String resumo;

	private TIPO tipo;

	private PRIORIDADE prioridade;

	private Ocorrencia primeiraOcorrencia;

	@Before()
	public void setup() {
		nomeEmpresa = "DelaRocaCompany";
		empresa = new Empresa(nomeEmpresa);
		nomeProjeto = "XY";
		empresa.adicionarProjeto(nomeProjeto);
		nomesFuncs = java.util.List.of("Fabio", "Bruno");
		empresa.adicionarFuncionario(nomesFuncs.get(0));
		empresa.adicionarFuncionario(nomesFuncs.get(1));
		projeto = empresa.obterProjetoPeloNome(nomeProjeto);
		projeto.adicionarFuncionario(nomesFuncs.get(0));
		projeto.adicionarFuncionario(nomesFuncs.get(1));
		id = "1";
		resumo = "Falha no servi�o X.";
		tipo = TIPO.BUG;
		prioridade = PRIORIDADE.ALTA;
		primeiraOcorrencia = new Ocorrencia(id, resumo, tipo, prioridade, nomesFuncs.get(0));
	}

	@Test()
	public void PodeSerAlteradoOFuncionarioResponsavelDeUmaOcorrenciaAberta() {
		primeiraOcorrencia.alterarFuncionarioResponsavel(nomesFuncs.get(1));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
		assertEquals(empresa.obterFuncionarioPeloNome(nomesFuncs.get(1)), primeiraOcorrencia.obterFuncionarioResponsavel());
	}

	@Test()
	public void naoPodeSerAlteradoOFuncionarioResponsavelDeUmaOcorrenciaCompletada() {
		primeiraOcorrencia.completarOcorrencia();
		primeiraOcorrencia.alterarFuncionarioResponsavel(nomesFuncs.get(1));
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
		assertEquals(ESTADO.ABERTA, primeiraOcorrencia.obterEstado());
		assertEquals(ESTADO.COMPLETADA, primeiraOcorrencia.obterEstado());
	}

	@Test()
	public void umaOcorrenciaNaoPodeReceberAlteracaoComUmFuncionarioResponsavelInexistente() {
		primeiraOcorrencia.alterarFuncionarioResponsavel("Jos�");
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
	}

	@Test()
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmFuncionarioResponsavelNulo() {
		primeiraOcorrencia.alterarFuncionarioResponsavel(null);
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
	}

	@Test()
	public void umaOcorrenciaNaoPodeReceberAlteracaoDeUmFuncionarioResponsavelVazio() {
		primeiraOcorrencia.alterarFuncionarioResponsavel("");
		assertEquals(nomesFuncs.get(0), primeiraOcorrencia.obterFuncionarioResponsavel().obterNome());
	}
}
