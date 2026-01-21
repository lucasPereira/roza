import org.junit.Test;

public class RefactoredTestClass17 {

	@Test()
	public void testeCriaFuncionario() {
		Empresa emp = new Empresa("Empresa x");
		String nome = "Joao";
		Funcionario result = emp.criaFuncionario(nome);
		assertEquals(nome, result.getNome());
	}
}
