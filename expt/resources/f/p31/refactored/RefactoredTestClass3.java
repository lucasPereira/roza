import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa Spotify");
		projeto = new Projeto("Projeto Primmer");
		empresa.cadastrarProjeto(projeto);
		empresa.cadastrarProjeto(this.projeto);
		funcionario = new Funcionario("Maria");
		empresa.cadastrarFuncionario(funcionario);
	}

	@Test()
	public void acimaDoLimiteDeOcorrenciaPorFuncionarioTest() {
		ArrayList<String> mensagemDeResultado = criarOcorrencia(11, funcionario);
		ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);
		assertThat(mensagemDeResultado.get(9), equalTo("Cadastro de ocorrencia com sucesso."));
		assertThat(mensagemDeResultado.get(10), equalTo("Funcionario já é responsavel por 10 ocorrencias."));
		assertThat(resultado.size(), equalTo(10));
	}

	@Test()
	public void limiteDeOcorrenciaPorFuncionarioTest() {
		ArrayList<String> mensagemDeResultado = criarOcorrencia(10, funcionario);
		ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);
		assertThat(mensagemDeResultado.get(9), equalTo("Cadastro de ocorrencia com sucesso."));
		assertThat(resultado.size(), equalTo(10));
		assertThat(resultado.get(9).obterTitulo(), equalTo("titulo 10"));
		assertThat(resultado.get(9).obterDescricao(), equalTo("descrição 10"));
		assertThat(resultado.get(0).obterSituacaoOcorrencia(), equalTo(EnumSituacaoOcorrencia.ABERTO));
	}
}
