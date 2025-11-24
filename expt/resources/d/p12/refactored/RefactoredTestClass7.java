import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Conta contaKarla;

	@Before()
	public void setup() {
		contaKarla = TesteAjudante.makeContaKarla();
	}

	@Test()
	public void checaAgenciaConta() {
		Boolean agenciaIgual = contaKarla.obterAgencia().obterNome().equals("BB Trindade");
		assertEquals(true, agenciaIgual);
	}

	@Test()
	public void checarTitularConta() {
		String nome = contaKarla.obterTitular();
		assertEquals("Karla Aparecida Justen", nome);
	}
}
