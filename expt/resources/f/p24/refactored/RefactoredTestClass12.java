import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void umaStringNulaNaoDeveSerAprovadaPeloHelpers() {
		String str = null;
		Helpers.stringValido(str);
	}
}
