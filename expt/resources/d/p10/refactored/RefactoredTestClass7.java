import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Dinheiro dezReais;

	private ValorMonetario dezReaisVM;

	@Before()
	public void setup() {
		dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		dezReaisVM = new ValorMonetario(Moeda.BRL).somar(dezReais);
	}

	@Test()
	public void testCriarValorMonetario() {
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		assertEquals(Moeda.BRL, vm.obterQuantia().obterMoeda());
		assertEquals(0, vm.obterQuantia().obterQuantiaEmEscala().intValue());
	}

	@Test()
	public void testSomaMoedasDiferentes() {
		ValorMonetario vm = new ValorMonetario(Moeda.USD);
		vm.somar(dezReais);
	}

	@Test()
	public void testSubtrairValorMonetario() {
		ValorMonetario valorMonetario = new ValorMonetario(Moeda.BRL);
		ValorMonetario vm = valorMonetario.subtrair(dezReais);
		assertEquals(dezReais.negativo().formatado(), vm.formatado());
	}

	@Test()
	public void testValoresMonetarioIguais() {
		ValorMonetario vm = new ValorMonetario(Moeda.BRL).somar(dezReais);
		assertEquals(vm, dezReaisVM);
	}
}
