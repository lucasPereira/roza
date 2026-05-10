import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void criarProjetoSemCodigo() {
		empresa.adicionarProjeto("");
		assertEquals(0, empresa.pegarNumeroDeProjetos());
	}
}
