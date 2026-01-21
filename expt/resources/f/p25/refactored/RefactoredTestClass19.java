import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass19 {

	@Before()
	public void setup() {
		ocorrencia.setPrioridade(PrioridadeOcorrencia.BAIXA);
	}

	@Test()
	public void modificaPrioridadeOcorrenciaBaixa() {
		assertEquals(PrioridadeOcorrencia.BAIXA, ocorrencia.getPrioridade());
	}

	@Test()
	public void trocaPrioridadelOcorrenciaAberta() {
		assertEquals(PrioridadeOcorrencia.BAIXA, ocorrencia.getPrioridade());
	}
}
