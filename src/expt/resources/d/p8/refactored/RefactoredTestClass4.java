import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Agencia agenciaItacorubi;

	private Conta contaCorrente;

	@Before()
	public void setup() {
		agenciaItacorubi = criarAgencia();
		contaCorrente = new Conta("Leandro Abrantes", 1, agenciaItacorubi);
	}

	@Test()
	public void criarContaCorrente() {
		assertEquals("Leandro Abrantes", contaCorrente.obterTitular());
	}

	@Test()
	public void obterAgenciaContaCorrente() {
		assertEquals(agenciaItacorubi, contaCorrente.obterAgencia());
	}

	@Test()
	public void obterTitularContaCorrente() {
		assertEquals("Leandro Abrantes", contaCorrente.obterTitular());
	}
}
