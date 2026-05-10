import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	@Before()
	public void setup() {
		this.sistemaBancario = new SistemaBancario();
		this.titularCaixaTrindade = "Ricardo";
		this.bancoCaixa = sistemaBancario.criarBanco("Caixa", Moeda.BRL);
		this.agenciaCaixaTrindade = bancoCaixa.criarAgencia("Trindade");
		this.contaCaixaTrindade = agenciaCaixaTrindade.criarConta(this.titularCaixaTrindade);
	}

	@Test()
	public void verificarRetornoCorretoDaAgencia() {
		Agencia agenciaRetornada = contaCaixaTrindade.obterAgencia();
		assertEquals(this.agenciaCaixaTrindade, agenciaRetornada);
	}

	@Test()
	public void verificarRetornoCorretoDoTitular() {
		String titularObtido = this.contaCaixaTrindade.obterTitular();
		assertEquals(this.titularCaixaTrindade, titularObtido);
	}

	@Test()
	public void verificarSeSaldoZero() {
		assertTrue(this.contaCaixaTrindade.calcularSaldo().zero());
	}
}
