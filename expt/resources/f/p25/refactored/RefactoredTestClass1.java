import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void empresaSemFuncionarios() {
		assertEquals(0, empresa.getFuncionarios().size());
	}
}
