import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass18 {

	@Before()
	public void setup() {
		CriaOcorrencias(10, projetoWPP, funcionarioLuiz);
	}

	@Test()
	public void atribuirOnzeOcorrencias() {
		assertEquals(11, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}

	@Test()
	public void fecharUmaOcorrenciaSemFuncionarioResponsavel() {
		projetoWPP.fecharOcorrencia(10);
		assertEquals(11, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(10, funcionarioLuiz.getNumeroOcorrencias());
	}
}
