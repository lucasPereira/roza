import org.junit.Test;

public class RefactoredTestClass12 {

	@Test()
	public void adicionasProjetos1e2() {
		Funcionario rafael = new Funcionario("Rafael");
		ArrayList<Projeto> projetos = new ArrayList<Projeto>();
		Projeto projeto1 = new Projeto("Projeto 1");
		rafael.adicionaProjeto(projeto1);
		projetos.add(projeto1);
		Projeto projeto2 = new Projeto("Projeto 2");
		rafael.adicionaProjeto(projeto2);
		projetos.add(projeto2);
		assertTrue(rafael.getListaProjetos().equals(projetos));
	}
}
