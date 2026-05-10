import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private String nome;

	private Projeto projeto;

	@Before()
	public void setup() {
		nome = "Gmail";
		projeto = new Projeto(nome);
	}

	@Test()
	public void alterarNomeProjeto() {
		String novoNome = "Youtube";
		projeto.setNome(novoNome);
		assertEquals(novoNome, projeto.getNomeProjeto());
	}

	@Test()
	public void criarProjeto() {
		assertEquals(nome, projeto.getNomeProjeto());
	}
}
