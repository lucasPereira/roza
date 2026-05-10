import org.junit.Test;

public class RefactoredTestClass34 {

	@Test()
	public void testeRetornaListaComUmFuncionario() {
		Empresa emp = new Empresa("Empresa x");
		String nome1 = "Joao";
		Funcionario f1 = emp.criaFuncionario(nome1);
		List<Funcionario> result = emp.retornaFuncionarios();
		assertEquals(f1, result.get(0));
	}
}
