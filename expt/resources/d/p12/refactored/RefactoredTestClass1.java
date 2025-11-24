import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void calcularSaldoConta() {
		Conta contaKarla = TesteAjudante.makeContaKarla();
		Dinheiro quantia = new Dinheiro(Moeda.BRL, 1000, 50);
		Transacao entrada = new Entrada(contaKarla, quantia);
		contaKarla.adicionarTransacao(entrada);
		assertEquals("+1000,50 BRL", contaKarla.calcularSaldo().formatado());
	}
}
