import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void criarProjeto() {
		empresaDeSoftware.criarProjeto("Calculadora");
		Integer numeroDeProjetos = empresaDeSoftware.obterNumeroDeProjetos();
		assertEquals(um, numeroDeProjetos);
	}
}
