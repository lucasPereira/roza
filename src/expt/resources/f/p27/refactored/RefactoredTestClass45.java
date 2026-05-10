import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass45 {

	private Ocorrencia oc;

	@Before()
	public void setup() {
		oc = new Ocorrencia(0, "resumo", Tipo.BUG, Prioridade.ALTA);
	}

	@Test()
	public void testeFinalizacaoOcorrencia() {
		boolean result = oc.finalizaOcorrencia();
		assertEquals(true, result);
	}

	@Test()
	public void testeTrocaPrioridadeOcorrenciaNull() {
		oc.trocaPrioridade(null);
	}

	@Test()
	public void trocaPrioridade() {
		oc.trocaPrioridade(Prioridade.MEDIA);
		assertEquals(Prioridade.MEDIA, oc.retornaPrioridade());
	}

	@Test()
	public void trocaPrioridadeDiferente() {
		oc.trocaPrioridade(Prioridade.BAIXA);
		assertEquals(Prioridade.BAIXA, oc.retornaPrioridade());
	}
}
