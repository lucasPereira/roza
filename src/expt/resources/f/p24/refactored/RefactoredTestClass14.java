import org.junit.Test;

public class RefactoredTestClass14 {

	@Test()
	public void umaStringVaziaNaoDeveSerAprovadaPeloHelpers() {
		String str = "";
		Helpers.stringValido(str);
	}
}
