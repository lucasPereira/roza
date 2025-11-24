import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Agencia agenciaTrindade;

	private Conta contaCorrenteLuisFernando;

	@Before()
	public void setup() {
		criarAgencia();
		agenciaTrindade = criarAgencia();
		contaCorrenteLuisFernando = new Conta("Luis Fernando", 1, agenciaTrindade);
	}

	@Test()
	public void criarContaCorrenteLuisFernando() {
		assertEquals("Luis Fernando", contaCorrenteLuisFernando.obterTitular());
	}

	@Test()
	public void obterAgenciaContaCorrenteLuisFernando() {
		assertEquals(agenciaTrindade, contaCorrenteLuisFernando.obterAgencia());
	}

	@Test()
	public void obterTitularContaCorrenteLuisFernando() {
		assertEquals("Luis Fernando", contaCorrenteLuisFernando.obterTitular());
	}
}
