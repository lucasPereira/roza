import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass42 {

	private Ocorrencia oc;

	@Before()
	public void setup() {
		oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
		oc.finalizaOcorrencia();
	}

	@Test()
	public void testeFinalizacaoOcorrenciaJaFinalizada() {
		boolean result = oc.finalizaOcorrencia();
		assertEquals(false, result);
	}

	@Test()
	public void testeOcorrenciaFoiFinalizada() {
		Estado result = oc.retornaEstado();
		assertEquals(Estado.FECHADO, result);
	}
}
