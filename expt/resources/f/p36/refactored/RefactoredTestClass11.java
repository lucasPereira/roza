import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private Ocorrencia melhoria;

	@Before()
	public void setup() {
		melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.alta);
	}

	@Test()
	public void mudarPrioridadeOcorrenciaBaixa() {
		melhoria.setPrioridade(PrioridadeOcorrencia.baixa);
		assertEquals(PrioridadeOcorrencia.baixa, melhoria.getPrioridade());
	}

	@Test()
	public void mudarPrioridadeOcorrenciaMedia() {
		melhoria.setPrioridade(PrioridadeOcorrencia.media);
		assertEquals(PrioridadeOcorrencia.media, melhoria.getPrioridade());
	}
}
