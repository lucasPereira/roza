import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void testProjeto() {
		Projeto p = new Projeto("RuneEscape", "002", juninho.id());
		empresa.addProjeto(p);
		assertEquals(p.nome(), "RuneEscape");
		assertEquals(p.id(), "002");
		assertEquals(p, empresa.getProjetoByID("002"));
		assertTrue(p.idResponsaveis().contains(juninho.id()));
	}
}
