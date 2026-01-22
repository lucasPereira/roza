import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void adicionaProjeto2() {
		Empresa empresa = new Empresa("empresa");
		Projeto projeto2 = new Projeto("Projeto02");
		empresa.adicionaProjeto(projeto2);
		assertTrue(empresa.temProjeto(projeto2));
	}
}
