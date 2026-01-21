import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass17 {

	private Empresa hexagon;

	@Before()
	public void setup() {
		hexagon = new Empresa("Hexagon");
	}

	@Test()
	public void adicionaLucas() {
		Funcionario lucas = new Funcionario("Lucas");
		hexagon.adicionaFuncionario(lucas);
		assertTrue(hexagon.temFuncionario(lucas));
	}

	@Test()
	public void criaHexagon() {
		assertEquals("Hexagon", hexagon.getNome());
	}
}
