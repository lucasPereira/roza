import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass20 {

	@Before()
	public void setup() {
		funcionario.terminaOcorrencia(ocorrencia);
	}

	@Test()
	public void ocorrenciaFechada() {
		assertEquals(EstadoOcorrencia.FECHADA, ocorrencia.getEstado());
	}

	@Test()
	public void trocaPrioridadeOcorrenciaFechada() {
		assertThrows(MudarOcorrenciaFechadaException.class, () -> {
			ocorrencia.setPrioridade(PrioridadeOcorrencia.BAIXA);
		}, "Uma ocorrencia fechada n�o pode ser modificada");
	}
}
