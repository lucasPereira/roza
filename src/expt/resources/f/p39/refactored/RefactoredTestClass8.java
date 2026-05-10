import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void criarProjetoComCodigo() {
		empresa.adicionarProjeto("IMW");
		assertEquals("IMW", empresa.pegarProjeto(0).pegarCodigoProjeto());
	}
}
