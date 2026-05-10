import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void criaProjeto() {
		Projeto projeto = new Projeto();
		List<Ocorrencia> ocorrencias = projeto.obterOcorrencias();
		assertEquals(0, ocorrencias.size());
	}
}
