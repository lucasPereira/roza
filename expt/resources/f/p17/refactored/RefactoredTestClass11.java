import org.junit.Test;

public class RefactoredTestClass11 {

	@Test()
	public void adicionasProjetos1e2() {
		Empresa hexagon = new Empresa("Hexagon");
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		Projeto projeto1 = new Projeto("Projeto 1");
		hexagon.adicionaProjeto(projeto1);
		projetos.add(projeto1);
		Projeto projeto2 = new Projeto("Projeto 2");
		hexagon.adicionaProjeto(projeto2);
		projetos.add(projeto2);
		assertTrue(hexagon.getListaProjetos().equals(projetos));
	}
}
