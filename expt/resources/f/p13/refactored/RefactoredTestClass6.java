import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void testFuncionarios() {
		Funcionario Fagundes = new Funcionario("Fagundes", "0000002");
		empresa.addFuncionario(Fagundes);
		assertEquals(Fagundes.nome(), "Fagundes");
		assertEquals(Fagundes.id(), "0000002");
		assertEquals(empresa.getFuncionarioByID("0000002"), Fagundes);
	}
}
