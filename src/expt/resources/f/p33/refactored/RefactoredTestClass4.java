import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void idDoFuncionario() {
		Funcionario funcionario2 = new Funcionario("Funcionario2", empresa1);
		assertEquals(0, funcionario1.getId());
		assertEquals(1, funcionario2.getId());
	}
}
