import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void criaProjetoPorEmpresa() {
		Empresa empresa = new Empresa("Test Company");
		Projeto projeto = empresa.criarProjeto();
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(1, projetos.size());
		assertEquals(projeto, empresa.getProjeto(0));
		assertEquals(0, projeto.getOcorrencias().size());
	}
}
