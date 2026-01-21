import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void criarProjetoComCodigo() {
		empresaBrasil.adicionarProjeto("IMW");
		assertEquals(1, empresaBrasil.getNumeroDeProjetos());
		assertEquals("IMW", empresaBrasil.getProjeto(0).getCodigoProjeto());
	}
}
