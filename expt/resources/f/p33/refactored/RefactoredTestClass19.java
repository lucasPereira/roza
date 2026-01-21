import org.junit.Test;

public class RefactoredTestClass19 {

	@Test()
	public void nomeVazioEmpresa() {
		assertThrows(NomeVazio.class, () -> new Empresa(""));
	}
}
