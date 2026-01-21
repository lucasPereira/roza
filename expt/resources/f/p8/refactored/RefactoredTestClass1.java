import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarFuncionario() {
		Empresa empresa = new Empresa();
		Funcionario alice = new Funcionario();
		Funcionario bob = new Funcionario();
		empresa.addFuncionario(alice);
		empresa.addFuncionario(bob);
		assertEquals(2, empresa.numFuncionarios());
	}
}
