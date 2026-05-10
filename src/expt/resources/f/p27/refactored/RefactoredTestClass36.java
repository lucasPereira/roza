import org.junit.Test;

public class RefactoredTestClass36 {

	@Test()
	public void testeTipo() {
		assertEquals(3, Tipo.values().length);
		assertEquals(Tipo.BUG, Tipo.values()[0]);
		assertEquals(Tipo.MELHORIA, Tipo.values()[1]);
		assertEquals(Tipo.TAREFA, Tipo.values()[2]);
	}
}
