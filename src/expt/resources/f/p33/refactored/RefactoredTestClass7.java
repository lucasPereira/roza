import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void inserirProjetoNaEmpresa() {
		assertTrue(empresa1.getProjetos().contains(projeto1));
	}
}
