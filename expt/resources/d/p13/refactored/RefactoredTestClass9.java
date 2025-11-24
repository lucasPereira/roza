import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private ValorMonetario valorMonetario;

	private Dinheiro dezDolares;

	@Before()
	public void setup() {
		valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		dezDolares = AjudanteDeTestes.retornaDezDolares();
	}

	@Test()
	public void testeSomaValorMonetarioInvalido() {
		valorMonetario.somar(dezDolares);
	}

	@Test()
	public void testeSubtraiValorMonetarioInvalido() {
		valorMonetario.subtrair(dezDolares);
	}
}
