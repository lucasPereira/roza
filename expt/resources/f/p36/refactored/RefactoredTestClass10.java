import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	@Before()
	public void setup() {
		projetoA.criarMelhoria("M1", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M2", joao, PrioridadeOcorrencia.baixa);
		projetoB.criarMelhoria("M3", joao, PrioridadeOcorrencia.media);
		projetoA.criarMelhoria("M4", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M5", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M6", joao, PrioridadeOcorrencia.alta);
		projetoB.criarMelhoria("M7", joao, PrioridadeOcorrencia.baixa);
		projetoA.criarMelhoria("M8", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M9", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M10", joao, PrioridadeOcorrencia.media);
	}

	@Test()
	public void funcionarioAtribuirDezOcorrenciasAbertas() {
		List<Ocorrencia> ocorrencias = joao.getOcorrenciasAbertas();
		assertEquals(10, ocorrencias.size());
	}

	@Test()
	public void funcionarioAtribuirOnzeOcorrenciasAbertas() {
		assertThrows(Exception.class, () -> projetoA.criarMelhoria("M11", joao, PrioridadeOcorrencia.alta));
	}
}
