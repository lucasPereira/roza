import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testeNomeFuncionario() {
		this.funcionario = new Funcionario("Betinho");
		String nomeFuncionario = "Betinho";
		assertEquals(nomeFuncionario, this.funcionario.getNome());
	}
}
