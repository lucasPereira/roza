import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criaValorMonetarioNulo() {
		Dinheiro reais_100 = new Dinheiro(Moeda.BRL, 110, 00);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		assertEquals(0, vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}
}
