import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private SistemaBancario sistemaBancario;

	private Banco bancoReal;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		bancoReal = sistemaBancario.criarBanco("Banco Real", Moeda.BRL);
	}

	@Test()
	public void criaAgencia() {
		Agencia trindade = bancoReal.criarAgencia("Trindade");
		assertEquals("Trindade", trindade.obterNome());
		assertEquals(bancoReal, trindade.obterBanco());
		assertEquals("001", trindade.obterIdentificador());
	}

	@Test()
	public void criaConta() {
		Agencia agenciaTrindade = bancoReal.criarAgencia("Trindade");
		Conta joseph = agenciaTrindade.criarConta("Joseph");
		assertEquals("Joseph", joseph.obterTitular());
		assertEquals(agenciaTrindade, joseph.obterAgencia());
		assertEquals("0001-6", joseph.obterIdentificador());
	}

	@Test()
	public void criarContaAdicionaNaAgencia() {
		Agencia trindade = bancoReal.criarAgencia("Trindade");
		Conta codigo1 = trindade.criarConta("Jose");
		Conta codigo2 = trindade.criarConta("Joseph");
		assertEquals("0002-6", codigo2.obterIdentificador());
	}

	@Test()
	public void testaContaSaldoNegativo() {
		Agencia agenciaTrindade = bancoReal.criarAgencia("Trindade");
		Conta joseph = agenciaTrindade.criarConta("Joseph");
		Dinheiro dezReais = Delegates.criaDezReais();
		Transacao saque = new Saida(joseph, dezReais);
		joseph.adicionarTransacao(saque);
		assertTrue(joseph.calcularSaldo().somar(dezReais).zero());
	}

	@Test()
	public void testaContaSaldoPositivo() {
		Agencia agenciaTrindade = bancoReal.criarAgencia("Trindade");
		Conta joseph = agenciaTrindade.criarConta("Joseph");
		Dinheiro dezReais = Delegates.criaDezReais();
		Transacao deposito = new Entrada(joseph, dezReais);
		joseph.adicionarTransacao(deposito);
		assertTrue(joseph.calcularSaldo().subtrair(dezReais).zero());
	}

	@Test()
	public void testaContaSaldoZeroCriacao() {
		Agencia agenciaTrindade = bancoReal.criarAgencia("Trindade");
		Conta joseph = agenciaTrindade.criarConta("Joseph");
		assertTrue(joseph.calcularSaldo().zero());
	}
}
