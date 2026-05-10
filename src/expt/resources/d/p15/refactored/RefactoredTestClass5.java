import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private SistemaBancario sistemaBancario;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
	}

	@Test()
	public void criarAgenciaAdiocionaNoBanco() {
		Banco bancoReal = Delegates.criaBancoReal(sistemaBancario);
		bancoReal.criarAgencia("Trindade");
		Agencia agenciaTrindade2 = bancoReal.criarAgencia("Trindade2");
		assertEquals("Compara codigo", "002", agenciaTrindade2.obterIdentificador());
	}

	@Test()
	public void criarBancoBRL() {
		Banco bancoReal = Delegates.criaBancoReal(sistemaBancario);
		assertEquals("Banco Real", bancoReal.obterNome());
		assertEquals(Moeda.BRL, bancoReal.obterMoeda());
	}

	@Test()
	public void criarBancoCHF() {
		Banco bancoCHF = sistemaBancario.criarBanco("BancoCHF", Moeda.CHF);
		assertEquals("BancoCHF", bancoCHF.obterNome());
		assertEquals(Moeda.CHF, bancoCHF.obterMoeda());
	}

	@Test()
	public void criarBancoUSD() {
		Banco bancoDolar = sistemaBancario.criarBanco("BancoDolar", Moeda.USD);
		assertEquals("BancoDolar", bancoDolar.obterNome());
		assertEquals(Moeda.USD, bancoDolar.obterMoeda());
	}
}
