import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void testeCriaProjetoY() {
		empresa.criaProjeto("Projeto Y");
		assertEquals("Projeto Y", empresa.obterProjeto(0));
	}
}
