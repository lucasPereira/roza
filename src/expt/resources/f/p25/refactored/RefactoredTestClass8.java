import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void naoModificaPrioridadeOcorrencia() {
		assertEquals(PrioridadeOcorrencia.ALTA, ocorrencia.getPrioridade());
	}
}
