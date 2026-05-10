import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	private Empresa minhaEmpresa;

	private Projeto meuProjeto;

	@Before()
	public void setup() {
		minhaEmpresa = new Empresa();
		meuProjeto = new Projeto();
		minhaEmpresa.cadastrarProjeto(meuProjeto);
	}

	@Test()
	public void cadastrarProjeto() {
		assertEquals(1, minhaEmpresa.getProjetos().size());
		assertTrue(minhaEmpresa.getProjetos().contains(meuProjeto));
	}

	@Test()
	public void cadastrarProjeto_jaCadastrado() {
		List<Projeto> listaDeProjetos = new LinkedList<>(minhaEmpresa.getProjetos());
		try {
			minhaEmpresa.cadastrarProjeto(meuProjeto);
		} catch (ProjetoJaCadastrado e) {
			assertEquals(listaDeProjetos, minhaEmpresa.getProjetos());
			throw e;
		}
	}
}
