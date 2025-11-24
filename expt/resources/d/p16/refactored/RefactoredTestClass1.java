import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void saldoConta() {
		Conta novaConta = TesteSetup.criaConta();
		Dinheiro nove = new Dinheiro(Moeda.BRL, 9, 0);
		Entrada entrada = new Entrada(novaConta, nove);
		novaConta.adicionarTransacao(entrada);
		ValorMonetario saldoConta = novaConta.calcularSaldo();
		assertEquals("9,00 BRL", saldoConta.obterQuantia().formatado());
	}
}
