import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void obterNomeDaEmpresa() {
		String nomeDaEmpresa = empresaDeSoftware.obterNome();
		assertEquals("Empresa de Software", nomeDaEmpresa);
	}
}
