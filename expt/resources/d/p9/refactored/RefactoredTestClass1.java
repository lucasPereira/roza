import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void calcularComTransacao() {
		this.conta = TestHelper.criarNovaConta();
		Transacao entrada = new Entrada(conta, TestHelper.criarDinheiroCemReais());
		conta.adicionarTransacao(entrada);
		ValorMonetario valorMonetario = conta.calcularSaldo();
		assertEquals("+100,00 BRL", valorMonetario.formatado());
	}
}
