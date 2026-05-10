import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void criarOcorrenciaComResumo() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		assertEquals("Arrumar margem do componente", projeto.pegarOcorrencia(0).pegarResumo());
	}
}
