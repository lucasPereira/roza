import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionaFuncionario1() {
		Empresa empresa = new Empresa("empresa");
		Funcionario rafael = new Funcionario("Funcionario01");
		empresa.adicionaFuncionario(rafael);
		assertTrue(empresa.temFuncionario(rafael));
	}
}
