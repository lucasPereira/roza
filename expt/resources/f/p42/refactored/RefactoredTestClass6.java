import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void testeCriaFuncionarioJorge() {
		empresa.criaFuncionario("Jorge da Silva");
		assertEquals("Jorge da Silva", empresa.obterFuncionario(0));
	}
}
