import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void umaStringDeSomenteEspacosNaoDeveSerAprovadaPeloHelpers() {
		String str = "  ";
		Helpers.stringValido(str);
	}
}
