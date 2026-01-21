import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void cadastrarFuncionario_2() {
		Empresa minhaEmpresa = new Empresa();
		Funcionario primeiroFuncionario = new Funcionario();
		minhaEmpresa.cadastrarFuncionario(primeiroFuncionario);
		Funcionario segundoFuncionario = new Funcionario();
		minhaEmpresa.cadastrarFuncionario(segundoFuncionario);
		assertEquals(2, minhaEmpresa.getFuncionarios().size());
		assertTrue(minhaEmpresa.getFuncionarios().contains(primeiroFuncionario));
		assertTrue(minhaEmpresa.getFuncionarios().contains(segundoFuncionario));
	}
}
