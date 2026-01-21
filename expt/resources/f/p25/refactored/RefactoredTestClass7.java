import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void modificaPrioridadeOcorrenciaMedia() {
		ocorrencia.setPrioridade(PrioridadeOcorrencia.MEDIA);
		assertEquals(PrioridadeOcorrencia.MEDIA, ocorrencia.getPrioridade());
	}
}
