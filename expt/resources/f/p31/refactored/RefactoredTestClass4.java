import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa Spotify");
		projeto = new Projeto("Projeto Primmer");
		empresa.cadastrarProjeto(projeto);
		funcionario = new Funcionario("Maria");
	}

	@Test()
	public void pegarNomeDoFuncionarioTest() {
		assertThat("Maria", hasToString(funcionario.obterNome()));
	}

	@Test()
	public void pegarOcorrenciasAtribuidasParaOFuncionarioTest() {
		empresa.cadastrarFuncionario(funcionario);
		Ocorrencia ocorrencia = new Ocorrencia("titulo", "descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);
		String resultadoCadastro = projeto.cadastrarOcorrencia(ocorrencia);
		ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);
		assertThat(resultado.size(), equalTo(1));
		assertThat(resultado.get(0).obterFuncionarioResponsavel(), equalTo(funcionario));
		assertThat(resultado.get(0), equalTo(ocorrencia));
		assertThat(resultadoCadastro, equalTo("Cadastro de ocorrencia com sucesso."));
	}

	@Test()
	public void pegarOcorrenciasDoFuncionarioQueNaoTemOcorenciaTest() {
		empresa.cadastrarFuncionario(funcionario);
		ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);
		assertThat(resultado.size(), equalTo(0));
	}
}
