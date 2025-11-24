import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Conta leandroConta;

	private Operacao operacao;

	@Before()
	public void setup() {
		leandroConta = this.novaContaComDezReaisDeSaldo();
		operacao = this.getSistemaBancario().sacar(leandroConta, new Dinheiro(Moeda.BRL, 5, 0));
	}

	@Test()
	public void operacaoSaque() {
		assertEquals(EstadosDeOperacao.SUCESSO, operacao.obterEstado());
		assertEquals(new Dinheiro(Moeda.BRL, 5, 0).toString(), leandroConta.calcularSaldo().formatarSemSinal());
	}

	@Test()
	public void saqueSucessoErro() {
		ValorMonetario zero = new ValorMonetario(Moeda.BRL);
		assertNotEquals(zero.obterQuantia().formatado(), leandroConta.calcularSaldo().obterQuantia().formatado());
	}
}
