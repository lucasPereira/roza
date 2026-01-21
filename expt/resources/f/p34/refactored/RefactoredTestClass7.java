import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void criarOcorrenciaSemResumo() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "");
		assertEquals("Falha ao criar a tarefa", projetoWPP.getOcorrencia(0).getResumo());
	}
}
