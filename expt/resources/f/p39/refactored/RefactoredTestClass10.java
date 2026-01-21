import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void testarCriacaoEmpresa() {
		String nomeEmpresa = "Empresa Grande";
		empresaX = new Empresa(nomeEmpresa);
		assertEquals(nomeEmpresa, empresaX.pegarNomeDaEmpresa());
		assertEquals(0, empresaX.pegarNumeroDeFuncionarios());
	}
}
