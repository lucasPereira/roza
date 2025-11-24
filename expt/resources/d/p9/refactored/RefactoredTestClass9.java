import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	@Before()
	public void setup() {
		this.agenciaTrindade = TestHelper.criarAgenciaTrindade();
	}

	@Test()
	public void criarConta() {
		Conta conta = agenciaTrindade.criarConta("Maria");
		assertEquals("Maria", conta.obterTitular());
		assertEquals("0001-5", conta.obterIdentificador());
	}

	@Test()
	public void obterBanco() {
		Banco banco = agenciaTrindade.obterBanco();
		assertEquals("Banco do Brasil", banco.obterNome());
	}

	@Test()
	public void obterIdentificador() {
		String identificador = agenciaTrindade.obterIdentificador();
		assertEquals("001", identificador);
	}

	@Test()
	public void obterNome() {
		String nome = agenciaTrindade.obterNome();
		assertEquals("Trindade", nome);
	}
}
