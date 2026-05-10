import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa empresa;

	private Projeto proj1;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		proj1 = new Projeto("Pixel 5a");
		empresa.addProj(proj1);
	}

	@Test()
	public void empresaDoisProjeto() {
		Projeto proj2 = new Projeto("Pixel 4a");
		empresa.addProj(proj2);
		List<Projeto> projetos = empresa.getProjetos();
		String nomeProjeto1 = empresa.getProjetos().get(0).getName();
		String nomeProjeto2 = empresa.getProjetos().get(1).getName();
		assertEquals(2, projetos.size());
		assertEquals("Pixel 5a", nomeProjeto1);
		assertEquals("Pixel 4a", nomeProjeto2);
	}

	@Test()
	public void empresaUmProjeto() {
		List<Projeto> projetos = empresa.getProjetos();
		String nomeProjeto = empresa.getProjetos().get(0).getName();
		assertEquals(1, projetos.size());
		assertEquals("Pixel 5a", nomeProjeto);
	}
}
