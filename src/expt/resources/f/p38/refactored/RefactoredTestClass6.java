import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void shouldCorrectlyAddFuncionario() {
		this.empresa.addFuncionario(new Funcionario("Ana"));
		assertThat(empresa.getListaFuncionariosSize(), equalTo(1));
	}
}
