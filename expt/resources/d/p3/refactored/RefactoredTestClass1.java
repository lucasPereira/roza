import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Dinheiro vinteBRL;

	private SistemaBancario sistemaBancario0;

	private Banco caixa;

	private Agencia agenciaCaixaTrindade;

	private Conta contaCaixa;

	private ValorMonetario meuValorMonetario;

	@Before()
	public void setup() {
		vinteBRL = new Dinheiro(Moeda.BRL, 20, 0);
		sistemaBancario0 = new SistemaBancario();
		caixa = sistemaBancario0.criarBanco("Caixa", Moeda.BRL);
		agenciaCaixaTrindade = caixa.criarAgencia("Agencia da Trindade");
		contaCaixa = agenciaCaixaTrindade.criarConta("Usuario");
		meuValorMonetario = new ValorMonetario(Moeda.BRL);
	}

	@Test()
	public void criaAgencia() {
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancarioComBancos();
		Agencia agenciaBBtrindade = sistemaBancario.obterBancos().get(1).criarAgencia("Agencia da Trindade");
		assertEquals("Agencia da Trindade", agenciaBBtrindade.obterNome());
		assertEquals("001", agenciaBBtrindade.obterIdentificador());
		assertEquals(sistemaBancario.obterBancos().get(1), agenciaBBtrindade.obterBanco());
	}

	@Test()
	public void criaBanco() {
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancario();
		Banco bradesco = sistemaBancario.criarBanco("Bradesco", Moeda.BRL);
		assertEquals("Bradesco", bradesco.obterNome());
		assertEquals(Moeda.BRL, bradesco.obterMoeda());
	}

	@Test()
	public void criaConta() {
		SistemaBancario sistemaBancario = TestHelper.criaSistemaBancarioComBancos();
		Agencia agenciaBBtrindade = sistemaBancario.obterBancos().get(1).criarAgencia("Agencia da Trindade");
		Conta minhaConta = agenciaBBtrindade.criarConta("Rafael");
		assertEquals("Rafael", minhaConta.obterTitular());
		assertEquals("0001-6", minhaConta.obterIdentificador());
		assertEquals(agenciaBBtrindade, minhaConta.obterAgencia());
	}

	@Test()
	public void criaDinheiro() {
		Integer valorEmEscala = 9080;
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 90, 80);
		assertEquals(Moeda.BRL, dinheiro.obterMoeda());
		assertEquals(valorEmEscala, dinheiro.obterQuantiaEmEscala());
		assertEquals("90,80 BRL", dinheiro.toString());
	}

	@Test()
	public void criaValorMonetario() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		assertTrue(valorMonetario.zero());
		assertEquals("0,00", valorMonetario.toString());
	}

	@Test()
	public void somaValorMonetario() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		valorMonetario = valorMonetario.somar(vinteBRL);
		assertEquals("+20,00 BRL", valorMonetario.toString());
	}

	@Test()
	public void subtraiValorMonetario() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		valorMonetario = valorMonetario.subtrair(vinteBRL);
		assertEquals("-20,00 BRL", valorMonetario.toString());
	}

	@Test()
	public void testaCalculaSaldo() {
		meuValorMonetario = contaCaixa.calcularSaldo();
		assertEquals("0,00", meuValorMonetario.toString());
	}

	@Test()
	public void toStringIgualAZero() {
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 0, 0);
		assertEquals("0,00", dinheiro.toString());
	}

	@Test()
	public void toStringMaiorQueZero() {
		Dinheiro dinheiro = new Dinheiro(Moeda.BRL, 90, 80);
		assertEquals("90,80 BRL", dinheiro.toString());
	}
}
