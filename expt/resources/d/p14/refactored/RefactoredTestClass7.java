import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Dinheiro milReais;

	private Banco bradesco;

	@Before()
	public void setup() {
		milReais = new Dinheiro(Moeda.BRL, 1000, 0);
		bradesco = new Banco("Bradesco", Moeda.BRL, milReais);
	}

	@Test()
	public void criarAgenciaTrindade() {
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		assertEquals("Trindade", agenciaTrindade.obterNome());
	}

	@Test()
	public void criarBancoBradesco() {
		assertEquals("Bradesco", bradesco.obterNome());
	}

	@Test()
	public void verificarBancoAgenciaTrindade() {
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		assertEquals(bradesco, agenciaTrindade.obterBanco());
	}

	@Test()
	public void verificarNomeAgenciaTrindade() {
		Agencia agenciaTrindade = new Agencia("Trindade", 1, bradesco);
		assertEquals("Trindade", agenciaTrindade.obterNome());
	}

	@Test()
	public void verificarTipoMoedaBanco() {
		assertEquals(Moeda.BRL, bradesco.obterMoeda());
	}
}
