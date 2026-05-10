import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void contratarFuncionarioVazio() {
		empresaY.contratarFuncionario("");
		assertEquals(1, empresaY.pegarNumeroDeFuncionarios());
	}
}
