import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private SistemaBancario sistemaBancario;

	private Banco bancoCaixa;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
	}

	@Test()
	public void criarAgenciaEVerificarRetornoCorretoDoBanco() {
		Agencia agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		Banco bancoDaAgenciaTrindade = agenciaCaixaTrindade.obterBanco();
		assertEquals(bancoDaAgenciaTrindade, bancoCaixa);
	}

	@Test()
	public void criarAgenciaEVerificarRetornoCorretoDoNome() {
		Agencia agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		String nomeAgenciaCaixaTrindadeObtido = agenciaCaixaTrindade.obterNome();
		assertEquals("Trindade", nomeAgenciaCaixaTrindadeObtido);
	}

	@Test()
	public void criarBancoEVerificarMoeda() {
		assertEquals(bancoCaixa.obterMoeda(), Moeda.BRL);
	}

	@Test()
	public void criarBancoEVerificarNome() {
		assertEquals(bancoCaixa.obterNome(), "Caixa");
	}
}
