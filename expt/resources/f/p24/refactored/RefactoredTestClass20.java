import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass20 {

	private String nomeProjeto;

	private Projeto projeto;

	@Before()
	public void setup() {
		nomeProjeto = "XY";
		projeto = new Projeto(nomeProjeto);
	}

	@Test()
	public void umProjetoCriadoDeveTerUmNome() {
		assertEquals(nomeProjeto, projeto.obterNome());
	}

	@Test()
	public void umProjetoCriadoDeveTerUmaListaDeFuncionariosVazia() {
		assertEquals(0, projeto.obterFuncionarios().size());
	}

	@Test()
	public void umProjetoCriadoIniciaComUltimoIDValorZero() {
		assertThat(0, is(projeto.obterUltimoID()));
	}

	@Test()
	public void umProjetoTemZeroOcorrencias() {
		assertEquals(0, projeto.obterOcorrencias().size());
	}
}
