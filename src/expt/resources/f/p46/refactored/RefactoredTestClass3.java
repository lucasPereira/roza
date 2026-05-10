import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void associarFuncionarioAoProjeto() {
		empresaDeSoftware.associarFuncionarioProjeto(gustavoKundlatsch, projetoCalculadora);
		Integer numeroDeProjetos = gustavoKundlatsch.obterNumeroDeProjetos();
		assertEquals(um, numeroDeProjetos);
	}
}
