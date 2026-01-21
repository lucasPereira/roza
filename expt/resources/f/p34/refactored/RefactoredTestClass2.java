import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void atribuirDezOcorrencias() {
		CriaOcorrencias(9, projetoWPP, funcionarioLuiz);
		assertEquals(10, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}
}
