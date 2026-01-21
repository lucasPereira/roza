import org.junit.Test;

public class RefactoredTestClass25 {

	@Test()
	public void testeEstado() {
		assertEquals(2, Estado.values().length);
		assertEquals(Estado.ABERTO, Estado.values()[0]);
		assertEquals(Estado.FECHADO, Estado.values()[1]);
	}
}
