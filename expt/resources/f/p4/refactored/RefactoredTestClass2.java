import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionarNovoFuncionario() {
		Empresa empresa = new Empresa();
		Funcionario joao = new Funcionario();
		empresa.adicionarFuncionario(joao);
		List<Funcionario> funcionarios = empresa.obterFuncionarios();
		assertEquals(1, funcionarios.size());
	}
}
