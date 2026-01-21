import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void testeCriaProjetoX() {
		empresa.criaProjeto("Projeto X");
		assertEquals("Projeto X", empresa.obterProjeto(0));
	}
}
