import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void empresaComVariosFuncionariosTest() {
		Empresa empresa = new Empresa("Empresa Spotify");
		empresa.cadastrarFuncionario(new Funcionario("Ana"));
		empresa.cadastrarFuncionario(new Funcionario("Maria"));
		assertThat(2, equalTo(empresa.getListaDeFuncionarios().size()));
		assertThat(pegarNomeDosFuncionarios(), hasItems("Ana", "Maria"));
	}
}
