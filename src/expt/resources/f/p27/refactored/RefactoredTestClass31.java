import org.junit.Test;

public class RefactoredTestClass31 {

	@Test()
	public void testePrioridadeOcorrenciaNull() {
		Ocorrencia oc = new Ocorrencia(0, "resumo", Tipo.BUG, null);
	}
}
