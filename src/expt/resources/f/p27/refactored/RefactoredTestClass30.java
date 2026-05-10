import org.junit.Test;

public class RefactoredTestClass30 {

	@Test()
	public void testePrioridade() {
		assertEquals(3, Prioridade.values().length);
		assertEquals(Prioridade.ALTA, Prioridade.values()[0]);
		assertEquals(Prioridade.MEDIA, Prioridade.values()[1]);
		assertEquals(Prioridade.BAIXA, Prioridade.values()[2]);
	}
}
