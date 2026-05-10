import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	@Before()
	public void setup() {
		this.empresa = new Empresa("Empresa Spotify");
		this.projeto = new Projeto("Projeto Primmer");
		empresa.cadastrarProjeto(projeto);
	}

	@Test()
	public void buscarOcorrenciaTest() {
		Funcionario funcionario = new Funcionario("Maria");
		Ocorrencia ocorrencia = new Ocorrencia("titulo", "descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);
		empresa.buscarProjeto(projeto.obterId()).cadastrarOcorrencia(ocorrencia);
		Ocorrencia resultado = empresa.buscarProjeto(projeto.obterId()).buscarOcorrencia(ocorrencia.obterId());
		assertThat(resultado, equalTo(ocorrencia));
	}

	@Test()
	public void cadastrarOcorrenciaTest() {
		Funcionario funcionario = new Funcionario("Maria");
		Ocorrencia ocorrencia = new Ocorrencia("titulo", "descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);
		String resultado = empresa.buscarProjeto(projeto.obterId()).cadastrarOcorrencia(ocorrencia);
		assertThat(resultado, equalTo("Cadastro de ocorrencia com sucesso."));
		assertThat(empresa.buscarProjeto(projeto.obterId()).obterOcorrencias().get(0).obterTitulo(), equalTo("titulo"));
		assertThat(empresa.buscarProjeto(projeto.obterId()).obterOcorrencias().get(0).obterDescricao(), equalTo("descrição"));
		assertThat(empresa.buscarProjeto(projeto.obterId()).obterOcorrencias().get(0).obterPrioridadeOcorrencia(), equalTo(EnumPrioridadeOcorrencia.BAIXA));
		assertThat(empresa.buscarProjeto(projeto.obterId()).obterOcorrencias().get(0).obterFuncionarioResponsavel(), equalTo(funcionario));
	}

	@Test()
	public void pegarIdDoProjetoTest() {
		assertThat(empresa.getListaDeProjetos().get(0).obterId(), equalTo(1l));
	}

	@Test()
	public void pegarNomeDoProjetoTest() {
		assertThat(empresa.getListaDeProjetos().get(0).obterNome(), equalTo("Projeto Primmer"));
	}
}
