import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void contratarFuncionarioVazio() {
		empresaBrasil.contratarFuncionario("");
		assertEquals(1, empresaBrasil.getNumeroDeFuncionarios());
	}
}
