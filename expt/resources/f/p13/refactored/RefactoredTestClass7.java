import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void testFuncionarios() {
		Funcionario Godofredo = new Funcionario("Godofredo", "0000001");
		empresa.addFuncionario(Godofredo);
		assertNotNull(empresa.funcionarios());
		assertEquals(empresa.getFuncionarioByID("0000001").id(), Godofredo.id());
	}
}
