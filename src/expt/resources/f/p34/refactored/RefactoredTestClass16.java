import org.junit.Test;

public class RefactoredTestClass16 {

	@Test()
	public void trocarUmFuncionarioDeUmaOcorrencia() {
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		assertEquals(1, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
}
