import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		ocorrencia = projeto.criaOcorrencia();
	}

	@Test()
	public void testeCriaResumoParaOcorrencia() {
		ocorrencia.criaResumo("Este � o resumo da Ocorrencia 1");
		assertEquals("Este � o resumo da Ocorrencia 1", projeto.obterOcorrencia(0).obterResumo());
	}

	@Test()
	public void testeCriaUmaOcorrenciaDoProjetoX() {
		assertEquals(ocorrencia, projeto.obterOcorrencia(0));
	}
}
