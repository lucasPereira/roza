import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Dinheiro reais_100;

	private Dinheiro reais_50;

	@Before()
	public void setup() {
		reais_100 = new Dinheiro(Moeda.BRL, 100, 00);
		reais_50 = new Dinheiro(Moeda.BRL, 50, 00);
	}

	@Test()
	public void criaDinheiro() {
		Dinheiro reais = new Dinheiro(Moeda.BRL, 100, 00);
		assertEquals(true, reais.equals(this.reais_100));
	}

	@Test()
	public void criaDinheiroDiferente() {
		assertNotEquals(true, this.reais_50.equals(this.reais_100));
	}

	@Test()
	public void criaDinheiro_DelegatedSetup() {
		Dinheiro reais = TesteDinheiroHelper.criaDinheiro();
		Moeda moeda_reais = reais.obterMoeda();
		assertEquals(Moeda.BRL, moeda_reais);
		assertEquals(true, reais.equals(this.reais_100));
	}

	@Test()
	public void criaDinheiro_FracionadoNegativo() {
		Dinheiro reais = new Dinheiro(Moeda.BRL, 100, 00);
		assertEquals(true, reais.equals(this.reais_100));
	}

	@Test()
	public void teste_GetMoeda() {
		Dinheiro reais = new Dinheiro(Moeda.BRL, 100, 00);
		Moeda moeda_reais = reais.obterMoeda();
		assertEquals(Moeda.BRL, moeda_reais);
	}

	@Test()
	public void teste_GetValorEmEscala() {
		Integer valorEmEscala = reais_100.obterQuantiaEmEscala();
		Moeda moeda = reais_100.obterMoeda();
		assertEquals(Moeda.BRL, moeda);
		assertEquals(10000, valorEmEscala.intValue());
	}
}
