import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void umEnumValidoDeveSerAprovadaPeloHelpers() {
		assertEquals(true, Helpers.enumValido(ESTADO.ABERTA));
	}
}
