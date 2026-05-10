import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Moeda moeda;

	private Dinheiro zero;

	private ValorMonetario vm;

	@Before()
	public void setup() {
		moeda = Moeda.USD;
		zero = new Dinheiro(moeda, 0, 0);
		vm = new ValorMonetario(moeda);
	}

	@Test()
	public void add() {
		Dinheiro current = vm.obterQuantia();
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm = vm.somar(mod);
		Dinheiro after = vm.obterQuantia();
		Integer result = after.obterQuantiaEmEscala() - current.obterQuantiaEmEscala();
		assertEquals(new Integer(500), result);
	}

	@Test()
	public void creationTest() {
		assertEquals(zero, vm.obterQuantia());
	}

	@Test()
	public void negative() {
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm = vm.subtrair(mod);
		assertEquals(vm.negativo(), true);
	}

	@Test()
	public void subtract() {
		Dinheiro current = vm.obterQuantia();
		Dinheiro mod = new Dinheiro(moeda, 5, 0);
		vm = vm.subtrair(mod);
		Dinheiro after = vm.obterQuantia();
		Integer result = current.obterQuantiaEmEscala() - after.obterQuantiaEmEscala();
		assertEquals(new Integer(-500), result);
	}
}
