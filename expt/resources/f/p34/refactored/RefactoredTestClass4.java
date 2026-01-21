import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criarDoisProjetos() {
		String codigoProjeto = "WPP";
		empresaBrasil.adicionarProjeto(codigoProjeto);
		codigoProjeto = "WPP2";
		empresaBrasil.adicionarProjeto(codigoProjeto);
		assertEquals(2, empresaBrasil.getNumeroDeProjetos());
	}
}
