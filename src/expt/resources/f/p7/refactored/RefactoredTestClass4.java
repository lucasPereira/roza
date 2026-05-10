import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void cadastrarProjeto_2() {
		Empresa minhaEmpresa = new Empresa();
		Projeto primeiroProjeto = new Projeto();
		minhaEmpresa.cadastrarProjeto(primeiroProjeto);
		Projeto segundoProjeto = new Projeto();
		minhaEmpresa.cadastrarProjeto(segundoProjeto);
		assertEquals(2, minhaEmpresa.getProjetos().size());
		assertTrue(minhaEmpresa.getProjetos().contains(primeiroProjeto));
		assertTrue(minhaEmpresa.getProjetos().contains(segundoProjeto));
	}
}
