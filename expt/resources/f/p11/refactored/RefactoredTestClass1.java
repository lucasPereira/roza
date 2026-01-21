import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testeNomeErradoFuncionario() {
		this.funcionario = new Funcionario("Betinho");
		String nomeFuncionarioErrado = "Marcos";
		assertNotEquals(nomeFuncionarioErrado, this.funcionario.getNome());
	}
}
