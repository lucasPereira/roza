import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private ValorMonetario valorMonetario;

	private Dinheiro dezReais;

	@Before()
	public void setup() {
		valorMonetario = AjudanteDeTestes.retornaValorMonetarioBrasileiro();
		dezReais = AjudanteDeTestes.retornaDezReais();
	}

	@Test()
	public void testeSomaValorMonetarioValido() {
		ValorMonetario novoValor = valorMonetario.somar(dezReais);
		assertEquals(dezReais, novoValor.obterQuantia());
		assertFalse(novoValor.negativo());
	}

	@Test()
	public void testeSubtraiValorMonetarioValido() {
		ValorMonetario novoValor = valorMonetario.subtrair(dezReais);
		assertTrue(novoValor.negativo());
		assertEquals(dezReais.negativo(), novoValor);
	}
}
