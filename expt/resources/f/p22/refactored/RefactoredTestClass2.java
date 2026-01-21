import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionaFuncionario2() {
		Empresa empresa = new Empresa("empresa");
		Funcionario lucas = new Funcionario("Funcionario02");
		empresa.adicionaFuncionario(lucas);
		assertTrue(empresa.temFuncionario(lucas));
	}
}
