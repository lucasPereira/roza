import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private SistemaBancario sistemaBancario;

	private Banco bb;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
		bb = sistemaBancario.criarBanco(BANCO_DO_BRASIL, Moeda.BRL);
	}

	@Test()
	public void agenciaTrindadeErro() {
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		assertNotEquals("002", bbTrindade.obterIdentificador());
		assertNotEquals("Campeche", bbTrindade.obterNome());
		assertNotEquals("Banco Brasil", bbTrindade.obterBanco().obterNome());
	}

	@Test()
	public void bancoDoBrasilErro() {
		assertNotEquals("Banco Brasil", bb.obterNome());
		assertNotEquals(Moeda.USD, bb.obterMoeda());
	}

	@Test()
	public void contaLeandroErro() {
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta contaLeandro = bbTrindade.criarConta(LEANDRO);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		assertNotEquals("0001-5", contaLeandro.obterIdentificador());
		assertNotEquals("Mario", contaLeandro.obterTitular());
		assertNotEquals(zero.somar(new Dinheiro(Moeda.BRL, 5, 0)), contaLeandro.calcularSaldo());
		assertNotEquals(bb.criarAgencia("Campeche"), contaLeandro.obterAgencia());
	}

	@Test()
	public void criacaoAgencia() {
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		assertEquals("001", bbTrindade.obterIdentificador());
		assertEquals(TRINDADE, bbTrindade.obterNome());
		assertEquals(bb, bbTrindade.obterBanco());
	}

	@Test()
	public void criacaoDeBanco() {
		assertEquals(BANCO_DO_BRASIL, bb.obterNome());
		assertEquals(Moeda.BRL, bb.obterMoeda());
	}

	@Test()
	public void criacaoDeConta() {
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbTrindade.criarConta(LEANDRO);
		assertEquals("0001-7", leandroConta.obterIdentificador());
		assertEquals(LEANDRO, leandroConta.obterTitular());
		assertEquals("0,00", leandroConta.calcularSaldo().formatado());
		assertEquals(TRINDADE, leandroConta.obterAgencia().obterNome());
	}

	@Test()
	public void operacaoDeposito() {
		Agencia bbTrindade = bb.criarAgencia(TRINDADE);
		Conta leandroConta = bbTrindade.criarConta(LEANDRO);
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = sistemaBancario.depositar(leandroConta, dezReais);
		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
		assertEquals(dezReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}
}
