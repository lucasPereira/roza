import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Petrobras");
	}

	@Test()
	public void EmpresaPetrobrasIniciaSemFuncionarios() {
		assertEquals(0, empresa.getFuncionarios().size());
	}

	@Test()
	public void EmpresaPetrobrasIniciaSemProejtos() {
		assertEquals(0, empresa.getProjetos().size());
	}

	@Test()
	public void criarEmpresaPetrobras() {
		assertEquals("Petrobras", empresa.getNome());
		assertEquals(0, empresa.getFuncionarios().size());
	}
}
