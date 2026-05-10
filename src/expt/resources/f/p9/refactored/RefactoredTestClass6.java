import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void testDemitirFuncionarioPedro() {
		Empresa empresa = new Empresa(cnpj, nome);
		Funcionario pedro = new Funcionario("Pedro");
		empresa.adicionarFuncionario(pedro);
		empresa.demitirFuncionario(pedro);
		assertTrue(empresa.funcionarios().isEmpty());
		assertFalse(empresa.funcionarios().contains(pedro));
	}
}
