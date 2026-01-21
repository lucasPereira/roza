import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void criarDoisProjetos() {
		String codigoProjeto = "IKF";
		empresaY.adicionarProjeto(codigoProjeto);
		empresaY.adicionarProjeto("IMW-1");
		assertEquals(2, empresaY.pegarNumeroDeProjetos());
	}
}
