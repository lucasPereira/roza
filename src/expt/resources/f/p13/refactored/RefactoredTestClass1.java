import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testAddProjetoComResponsavelInexistente() {
		Projeto p = new Projeto("RuneEscape", "002", "0000001");
		assertThrows(Exception.class, () -> {
			empresa.addProjeto(p);
		});
	}
}
