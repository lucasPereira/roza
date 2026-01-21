import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void testarCriacaoEmpresa() {
		String nomeEmpresa = "Empresa Brasil";
		empresaSemNome = new Empresa(nomeEmpresa);
		assertEquals(nomeEmpresa, empresaSemNome.getNomeDaEmpresa());
		assertEquals(0, empresaSemNome.getNumeroDeFuncionarios());
	}
}
