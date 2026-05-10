import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void criarProjetoSemCodigo() {
		empresaBrasil.adicionarProjeto("");
		assertEquals(0, empresaBrasil.getNumeroDeProjetos());
	}
}
