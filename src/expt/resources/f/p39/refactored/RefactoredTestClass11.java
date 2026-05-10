import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void testarCriacaoEmpresaSemNome() {
		String nomeEmpresa = "";
		empresaX = new Empresa(nomeEmpresa);
		assertEquals("Empresa sem nome", empresaX.pegarNomeDaEmpresa());
	}
}
