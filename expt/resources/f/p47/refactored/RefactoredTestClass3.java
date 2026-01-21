import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testNormalPriority() {
		int normalPriorityValue = PrioridadeOcorrencia.MEDIA.getPrioridade();
		assertEquals(normalPriorityValue, 1);
	}
}
