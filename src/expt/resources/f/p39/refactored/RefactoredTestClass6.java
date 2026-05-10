import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void criarOcorrenciaSemResumo() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "");
		assertEquals("Falha ao criar a tarefa", projeto.pegarOcorrencia(0).pegarResumo());
	}
}
