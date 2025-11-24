import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void testSomarValorMonetario() {
		Dinheiro dezReais = new Dinheiro(Moeda.BRL, 10, 0);
		ValorMonetario dezReaisVM = new ValorMonetario(Moeda.BRL).somar(dezReais);
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		Dinheiro somaEsperada = new Dinheiro(Moeda.BRL, 15, 0);
		ValorMonetario vm = new ValorMonetario(Moeda.BRL);
		ValorMonetario cincoReaisVM = vm.somar(cincoReais);
		ValorMonetario quinzeReaisVM = cincoReaisVM.somar(dezReais);
		assertEquals(somaEsperada, quinzeReaisVM.obterQuantia());
	}
}
