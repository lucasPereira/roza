import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void criaDoisProjetosPorEmpresa() {
		Empresa empresa = new Empresa("Test Company");
		Projeto projeto1 = empresa.criarProjeto();
		Projeto projeto2 = empresa.criarProjeto();
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(2, projetos.size());
		assertEquals(projeto1, empresa.getProjeto(0));
		assertEquals(projeto2, empresa.getProjeto(1));
	}
}
