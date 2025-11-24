import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void getQuantia() {
		Dinheiro reais_100 = new Dinheiro(Moeda.BRL, 110, 00);
		Dinheiro dinheiro = TesteValorMonetarioHelper.criaDinheiro();
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(dinheiro);
		assertEquals(new Dinheiro(Moeda.BRL, 100, 00), vm.obterQuantia());
	}
}
