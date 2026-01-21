import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void testHighPriority() {
		int highPriorityValue = PrioridadeOcorrencia.ALTA.getPrioridade();
		assertEquals(highPriorityValue, 2);
	}
}
