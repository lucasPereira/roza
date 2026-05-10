import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void somaDolarComReal() {
		ValorMonetario baseBRL = new ValorMonetario(Moeda.BRL);
		Dinheiro dezDolares = Delegates.criaDezDolares();
		ValorMonetario dez = baseBRL.somar(dezDolares);
	}
}
