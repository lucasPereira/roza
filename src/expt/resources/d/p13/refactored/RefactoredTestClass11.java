import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private SistemaBancario sistemaBancario;

	@Before()
	public void setup() {
		sistemaBancario = new SistemaBancario();
	}

	@Test()
	public void testeConstrutorBanco() {
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL);
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
	}

	@Test()
	public void testeConstrutorBancoInvalidoNomeRepetido() {
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL);
		assertEquals("Banco do Brasil", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
	}

	@Test()
	public void testeConstrutorBancoInvalidoNomeVazio() {
		Banco bancoDoBrasil = sistemaBancario.criarBanco("", Moeda.BRL);
		assertEquals("", bancoDoBrasil.obterNome());
		assertEquals(Moeda.BRL, bancoDoBrasil.obterMoeda());
	}
}
