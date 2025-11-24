import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testarObterMoedaCincoReais() {
		Dinheiro cincoReais = new Dinheiro(Moeda.BRL, 5, 0);
		assertEquals(Moeda.BRL, cincoReais.obterMoeda());
	}
}
