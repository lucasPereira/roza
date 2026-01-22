import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Projeto projeto;

	@Before()
	public void setup() {
		projeto = new Projeto("Projeto");
	}

	@Test()
	public void adicionaOcorrenciaA() {
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A");
		projeto.adicionaOcorrencia(ocorrenciaA);
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}

	@Test()
	public void criaProjeto() {
		assertEquals("Projeto", projeto.Nome());
	}
}
