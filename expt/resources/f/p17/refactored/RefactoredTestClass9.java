import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void adicionaProjeto2() {
		Empresa hexagon = new Empresa("Hexagon");
		Projeto projeto2 = new Projeto("Projeto 2");
		hexagon.adicionaProjeto(projeto2);
		assertTrue(hexagon.temProjeto(projeto2));
	}
}
