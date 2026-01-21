import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Projeto projeto;

	@Before()
	public void setup() {
		projeto = new Projeto("Projeto");
	}

	@Test()
	public void adicionaOcorrenciaB() {
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B");
		projeto.adicionaOcorrencia(ocorrenciaB);
		assertTrue(projeto.temOcorrencia(ocorrenciaB));
	}

	@Test()
	public void criaProjeto() {
		assertEquals("Projeto", projeto.Nome());
	}
}
