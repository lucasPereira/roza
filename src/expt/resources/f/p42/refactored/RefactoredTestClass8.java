import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void testeCriaProjetoXeY() {
		Projeto projetoX = empresa.criaProjeto("Projeto X");
		Projeto projetoY = empresa.criaProjeto("Projeto Y");
		List<Projeto> lista = new ArrayList<>();
		lista.add(projetoX);
		lista.add(projetoY);
		assertEquals("Projeto X", empresa.obterProjeto(0));
		assertEquals("Projeto Y", empresa.obterProjeto(1));
		assertEquals(lista, empresa.obterProjetos());
		assertEquals(2, empresa.obterProjetos().size());
	}
}
