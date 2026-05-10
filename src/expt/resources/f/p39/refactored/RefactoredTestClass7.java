import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void criarProjeto() {
		empresaY.adicionarProjeto("");
		assertEquals(1, empresaY.pegarNumeroDeProjetos());
	}
}
