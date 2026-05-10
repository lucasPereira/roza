import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass20 {

	@Before()
	public void setup() {
		projetoWPP.fecharOcorrencia(0);
	}

	@Test()
	public void fecharOcorrenciaAberta() {
		assertEquals(0, projetoWPP.getNumeroDeOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}

	@Test()
	public void trocarUmFuncionarioDeUmaOcorrenciaFechada() {
		projetoWPP.trocarFuncionarioResponsavel(0, funcionarioPaulo);
		assertEquals(0, funcionarioPaulo.getNumeroOcorrencias());
		assertEquals(0, funcionarioLuiz.getNumeroOcorrencias());
	}
}
