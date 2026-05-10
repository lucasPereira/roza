import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void inserirFuncionarioNaEmpresa() {
		assertTrue(empresa1.getFuncionarios().contains(funcionario1));
	}
}
