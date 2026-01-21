import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void mudarPrioridadeOcorrenciaAlta() {
		Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.media);
		melhoria.setPrioridade(PrioridadeOcorrencia.alta);
		assertEquals(PrioridadeOcorrencia.alta, melhoria.getPrioridade());
	}
}
