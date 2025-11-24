import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private SistemaBancario sistemaBancario;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		sistemaBancario.criarBanco("Santander", Moeda.BRL);
	}

	@Test()
	public void criarAgencia() {
		Agencia santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantanderTrindade");
		assertEquals("Santander", santanderTrindade.obterBanco().obterNome());
		assertEquals("SantanderTrindade", santanderTrindade.obterNome());
	}

	@Test()
	public void criarBanco() {
		assertEquals(1, sistemaBancario.obterBancos().size());
	}

	@Test()
	public void criarConta() {
		Agencia santanderTrindade = sistemaBancario.obterBancos().get(0).criarAgencia("SantanderTrindade");
		Conta contaSalario = santanderTrindade.criarConta("Vinicius Alves");
		assertEquals("SantanderTrindade", contaSalario.obterAgencia().obterNome());
	}
}
