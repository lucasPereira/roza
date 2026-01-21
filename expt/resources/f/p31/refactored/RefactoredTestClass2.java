import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void verificarAQuantidadeDeFuncionarioAposInserirProjetoTest() {
		Empresa empresa = new Empresa("Empresa Spotify");
		empresa.cadastrarFuncionario(new Funcionario("Maria"));
		empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(1));
		assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), equalTo("Maria"));
		assertThat(empresa.getListaDeProjetos().get(0).obterNome(), equalTo("Projeto Primmer"));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
	}
}
