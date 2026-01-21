import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void obterZeroProjetosDoFuncionario() {
		Integer numeroDeProjetos = gustavoKundlatsch.obterNumeroDeProjetos();
		assertEquals(zero, numeroDeProjetos);
	}
}
