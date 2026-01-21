import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void criarOcorrenciaComResumo() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		assertEquals("Arrumar margem do componente", projetoWPP.getOcorrencia(0).getResumo());
	}
}
