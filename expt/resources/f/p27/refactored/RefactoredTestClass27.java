import org.junit.Test;

public class RefactoredTestClass27 {

	@Test()
	public void testeListaFuncionarios() {
		Empresa emp = new Empresa("Empresa x");
		String nome1 = "Joao";
		String nome2 = "Maria";
		Funcionario func = emp.criaFuncionario(nome1);
		Funcionario func2 = emp.criaFuncionario(nome2);
		List<Funcionario> result = emp.retornaFuncionarios();
		assertEquals(2, result.size());
		assertEquals(func, result.get(0));
		assertEquals(func2, result.get(1));
	}
}
