import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarFuncionario() {
		empresaDeSoftware.adicionarFuncionario("Gustavo Kundlatsch");
		Integer numeroDeFuncionarios = empresaDeSoftware.obterNumeroDeFuncionarios();
		assertEquals(um, numeroDeFuncionarios);
	}
}
