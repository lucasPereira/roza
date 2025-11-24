import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	@Before()
	public void setup() {
		this.bancoDoBrasil = TestHelper.criarBancoDoBrasil();
	}

	@Test()
	public void criarAgencia() {
		Agencia agencia = bancoDoBrasil.criarAgencia("Trindade");
		assertEquals("Trindade", agencia.obterNome());
		assertEquals(1, Integer.parseInt(agencia.obterIdentificador()));
	}

	@Test()
	public void obterMoeda() {
		Moeda moeda = bancoDoBrasil.obterMoeda();
		assertEquals("Moeda", moeda, Moeda.BRL);
	}

	@Test()
	public void obterNome() {
		String nomeDoBanco = bancoDoBrasil.obterNome();
		assertEquals("Banco do Brasil", nomeDoBanco);
	}
}
