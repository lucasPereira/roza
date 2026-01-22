import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void adicionaProjeto1() {
		Empresa empresa = new Empresa("empresa");
		Projeto projeto1 = new Projeto("Projeto01");
		empresa.adicionaProjeto(projeto1);
		assertTrue(empresa.temProjeto(projeto1));
	}
}
