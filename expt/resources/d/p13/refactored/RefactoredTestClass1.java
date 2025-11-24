import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void efetuaTransacaoEntrada() {
		Conta contaJohn = AjudanteDeTestes.retornaContaJohnBancoDoBrasilAgTrindade();
		Dinheiro dezReais = AjudanteDeTestes.retornaDezReais();
		Transacao deposita10Reais = new Entrada(contaJohn, dezReais);
		ValorMonetario valor = new ValorMonetario(Moeda.BRL);
		valor.somar(dezReais);
		contaJohn.adicionarTransacao(deposita10Reais);
		assertEquals(valor, contaJohn.calcularSaldo());
	}
}
