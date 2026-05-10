import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void umaStringValidaDeveSerAprovadaPeloHelpers() {
		String str = "Fabio testando string";
		assertEquals(true, Helpers.stringValido(str));
	}
}
