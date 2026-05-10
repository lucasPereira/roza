import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void testLowPriority() {
		int lowPriorityValue = PrioridadeOcorrencia.BAIXA.getPrioridade();
		assertEquals(lowPriorityValue, 0);
	}
}
