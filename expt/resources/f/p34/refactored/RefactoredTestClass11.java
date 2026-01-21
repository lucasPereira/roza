import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void testarCriacaoEmpresaSemNome() {
		String nomeEmpresa = "";
		empresaSemNome = new Empresa(nomeEmpresa);
		assertEquals("Empresa sem nome", empresaSemNome.getNomeDaEmpresa());
	}
}
