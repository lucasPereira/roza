import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testeCriaFuncionarioJoao() {
		empresa.criaFuncionario("Joao da Silva");
		assertEquals("Joao da Silva", empresa.obterFuncionario(0));
	}
}
