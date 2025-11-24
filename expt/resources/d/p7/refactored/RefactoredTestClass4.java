import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	@Before()
	public void setup() {
		this.SB = new SistemaBancario();
		this.BB = this.SB.criarBanco("BB", Moeda.BRL);
		this.Agronomica = this.BB.criarAgencia("Agronomica");
		this.reais_00 = new Dinheiro(Moeda.BRL, 0, 0);
		this.Trindade = this.BB.criarAgencia("Trindade");
	}

	@Test()
	public void criaAgencia() {
		assertEquals(Moeda.BRL, this.Trindade.obterBanco().obterMoeda());
		assertEquals("BB", this.Trindade.obterBanco().obterNome());
		assertEquals("Trindade", this.Trindade.obterNome());
		assertEquals("002", this.Trindade.obterIdentificador());
	}

	@Test()
	public void criaConta() {
		this.conta_Ivan = this.Trindade.criarConta("Ivan");
		assertEquals(Trindade, this.conta_Ivan.obterAgencia());
		assertEquals("Ivan", this.conta_Ivan.obterTitular());
		assertEquals(this.reais_00.formatado(), this.conta_Ivan.calcularSaldo().formatarSemSinal());
	}
}
