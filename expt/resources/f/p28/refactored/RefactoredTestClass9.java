import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private String nome;

	private Empresa empresa;

	@Before()
	public void setup() {
		nome = "Google";
		empresa = new Empresa(nome);
	}

	@Test()
	public void alterarNomeEmpresa() {
		String novoNome = "Google Company";
		empresa.setNome(novoNome);
		assertEquals(novoNome, empresa.getNome());
	}

	@Test()
	public void criarEmpresa() {
		assertEquals(nome, empresa.getNome());
	}

	@Test()
	public void verificaProjetoPertenceEmpresa() {
		String nomeProjeto = "Google Docs";
		Projeto projeto = new Projeto(nomeProjeto);
		empresa.adicionarProjeto(projeto);
		assertTrue(empresa.projetoPertenceEmpresa(projeto));
	}
}
