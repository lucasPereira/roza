import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testAdicionarFuncionariaMaria() {
		Empresa empresa = new Empresa(cnpj, nome);
		Funcionario maria = new Funcionario("Maria");
		empresa.adicionarFuncionario(maria);
		assertEquals(1, empresa.funcionarios().size());
		assertTrue(empresa.funcionarios().contains(maria));
	}
}
