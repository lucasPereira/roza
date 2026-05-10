import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void adicionarMaisDeUmProjetoEmpresa() {
		String nome = "Google";
		Empresa empresa = new Empresa(nome);
		String nome1 = "Google Drive";
		Projeto projeto1 = new Projeto(nome1);
		String nome2 = "Youtube";
		Projeto projeto2 = new Projeto(nome2);
		empresa.adicionarProjeto(projeto1);
		empresa.adicionarProjeto(projeto2);
		assertEquals(2, empresa.getQuatidadeProjeto());
		assertEquals(projeto1, empresa.getProjeto(projeto1));
		assertEquals(projeto2, empresa.getProjeto(projeto2));
	}
}
