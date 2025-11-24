import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Dinheiro reais_100;

	private Dinheiro reais_300;

	@Before()
	public void setup() {
		reais_100 = new Dinheiro(Moeda.BRL, 110, 00);
		reais_300 = new Dinheiro(Moeda.BRL, 300, 00);
	}

	@Test()
	public void subtraiValor_InLine() {
		Dinheiro reais_150 = new Dinheiro(Moeda.BRL, 150, 00);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_300);
		vm = new ValorMonetario(Moeda.BRL).subtrair(reais_150);
		assertEquals(15000, vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}

	@Test()
	public void subtraiValor_MixedSetUp() {
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(reais_300);
		vm = new ValorMonetario(Moeda.BRL).subtrair(this.reais_100);
		assertEquals(11000, vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}
}
