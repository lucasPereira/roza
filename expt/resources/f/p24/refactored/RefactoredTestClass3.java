import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void umEnumNuloNaoDeveSerAprovadaPeloHelpers() {
		Helpers.enumValido(null);
	}
}
