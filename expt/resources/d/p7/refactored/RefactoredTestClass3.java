import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void somaValor() {
		Dinheiro reais_100 = new Dinheiro(Moeda.BRL, 110, 00);
		Dinheiro reais_100 = new Dinheiro(Moeda.BRL, 100, 00);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_100);
		assertEquals(10000, vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}
}
