import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa Spotify");
	}

	@Test()
	public void cadastrarFuncionarioTest() {
		empresa.cadastrarFuncionario(new Funcionario("João"));
		assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), equalTo("João"));
	}

	@Test()
	public void empresaSemFuncionarioTest() {
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));
	}

	@Test()
	public void empresaSemProjetoESemFuncionarioTest() {
		assertThat(empresa.getListaDeProjetos().size(), equalTo(0));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));
	}

	@Test()
	public void pegarNomeDaEmpresaTest() {
		String resultado = empresa.obterNomeDaEmpresa();
		assertThat("Empresa Spotify", equalTo(resultado));
	}

	@Test()
	public void pegarNomeFuncionarioTest() {
		empresa.cadastrarFuncionario(new Funcionario("João"));
		assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), hasToString("João"));
	}
}
