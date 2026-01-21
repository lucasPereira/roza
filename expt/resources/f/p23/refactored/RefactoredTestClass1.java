import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testeCriaProjeto() {
		Projeto proj = new Projeto("nome");
		assertEquals("nome", proj.nome());
	}
}
