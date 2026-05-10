import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private ValorMonetario zero;

	private ValorMonetario cinco;

	@Before()
	public void setup() {
		zero = new ValorMonetario(Moeda.BRL);
		cinco = zero.somar(new Dinheiro(Moeda.BRL, 5, 0));
	}

	@Test()
	public void comparacaoValorMonetarioDeMesmaQuantia() {
		ValorMonetario menosCinco = zero.subtrair(new Dinheiro(Moeda.BRL, 5, 0));
		assertNotEquals(cinco, menosCinco);
		assertEquals(cinco.obterQuantia().formatado(), menosCinco.obterQuantia().formatado());
	}

	@Test()
	public void comparacaoValorMonetarioDeMesmaQuantiaErro() {
		ValorMonetario menosDois = zero.subtrair(new Dinheiro(Moeda.BRL, 2, 0));
		assertNotEquals(cinco.obterQuantia().formatado(), menosDois.obterQuantia().formatado());
	}
}
