import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	@Before()
	public void setup() {
		empresa.contrataFuncionario();
	}

	@Test()
	public void contratarMultiplosFuncionario() {
		empresa.contrataFuncionario();
		assertEquals(2, empresa.getFuncionarios().size());
	}

	@Test()
	public void contratarUmFuncionario() {
		assertEquals(1, empresa.getFuncionarios().size());
	}
}
