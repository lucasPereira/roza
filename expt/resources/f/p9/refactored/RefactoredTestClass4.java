import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void testCancelarProjetoCasa() {
		Empresa empresa = new Empresa(cnpj, nome);
		Projeto casa = new Projeto("Casa");
		empresa.adicionarProjeto(casa);
		empresa.cancelarProjeto(casa);
		assertEquals(0, empresa.projetos().size());
		assertFalse(empresa.projetos().contains(casa));
	}
}
