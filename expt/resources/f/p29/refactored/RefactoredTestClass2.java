import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionarFuncionario() {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		newEmpresa.addFuncionario(stefanoFuncionario);
		assertEquals(stefanoFuncionario, newEmpresa.getFuncionario(1));
	}
}
