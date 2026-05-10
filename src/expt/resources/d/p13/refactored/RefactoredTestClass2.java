import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void efetuaTransacaoSaida() {
		Conta contaJohn = AjudanteDeTestes.retornaContaJohnBancoDoBrasilAgTrindade();
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		Transacao retira10Reais = new Saida(contaJohn, dezReais);
		contaJohn.adicionarTransacao(retira10Reais);
		assertTrue(contaJohn.calcularSaldo().negativo());
		assertTrue(contaJohn.calcularSaldo().somar(dezReais).zero());
	}
}
