import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Dinheiro quinhentosReais;

	@Before()
	public void setup() {
		quinhentosReais = new Dinheiro(Moeda.BRL, 500, 0);
	}

	@Test()
	public void criarAgencia() {
		Banco bancoBrasil = new Banco("Bradesco", Moeda.BRL, quinhentosReais);
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		assertEquals("Itacorubi", agenciaItacorubi.obterNome());
	}

	@Test()
	public void criarBancoBradesco() {
		Banco bancoBrasil = new Banco("Brasil", Moeda.BRL, quinhentosReais);
		assertEquals("Brasil", bancoBrasil.obterNome());
	}

	@Test()
	public void verificarBancoAgencia() {
		Banco bancoBrasil = new Banco("Bradesco", Moeda.BRL, quinhentosReais);
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		assertEquals(bancoBrasil, agenciaItacorubi.obterBanco());
	}

	@Test()
	public void verificarNomeAgencia() {
		Banco bancoBrasil = new Banco("Bradesco", Moeda.BRL, quinhentosReais);
		Agencia agenciaItacorubi = new Agencia("Itacorubi", 1, bancoBrasil);
		assertEquals("Itacorubi", agenciaItacorubi.obterNome());
	}

	@Test()
	public void verificarTipoMoedaBanco() {
		Banco bancoBrasil = new Banco("Brasil", Moeda.BRL, quinhentosReais);
		assertEquals(Moeda.BRL, bancoBrasil.obterMoeda());
	}
}
