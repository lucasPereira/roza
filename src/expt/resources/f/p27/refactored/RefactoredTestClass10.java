import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void testeAtribuiProjetoNullFuncionario() {
		Empresa emp = new Empresa("Empresa x");
		String nomeFunc = "João";
		Funcionario func = emp.criaFuncionario(nomeFunc);
		boolean result = emp.atribuiProjetoFuncionario(func, null);
		assertEquals(false, result);
	}
}
