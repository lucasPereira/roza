import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Conta leandroConta;

	@Before()
	public void setup() {
		leandroConta = this.novaConta();
	}

	@Test()
	public void criacaoDeConta() {
		assertEquals("0001-7", leandroConta.obterIdentificador());
		assertEquals(LEANDRO, leandroConta.obterTitular());
		assertEquals("0,00", leandroConta.calcularSaldo().formatado());
		assertEquals(TRINDADE, leandroConta.obterAgencia().obterNome());
	}

	@Test()
	public void operacaoDeposito() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = this.getSistemaBancario().depositar(leandroConta, dezReais);
		assertEquals(EstadosDeOperacao.SUCESSO, deposito.obterEstado());
		assertEquals(dezReais.toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void saldoAposDepositoSucessoErro() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		Operacao deposito = this.getSistemaBancario().depositar(leandroConta, dezReais);
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		assertNotEquals(zero.obterQuantia().formatado(), leandroConta.calcularSaldo().obterQuantia().formatado());
	}
}
