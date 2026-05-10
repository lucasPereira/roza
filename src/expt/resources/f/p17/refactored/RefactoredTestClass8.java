import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void adicionaProjeto1() {
		Empresa hexagon = new Empresa("Hexagon");
		Projeto projeto1 = new Projeto("Projeto 1");
		hexagon.adicionaProjeto(projeto1);
		assertTrue(hexagon.temProjeto(projeto1));
	}
}
