import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarDoisProjetos() {
		Projeto newProjeto = new Projeto(newEmpresa, 1);
		Projeto newProjeto2 = new Projeto(newEmpresa, 2);
		newEmpresa.addProjeto(newProjeto);
		newEmpresa.addProjeto(newProjeto2);
		assertEquals(newProjeto, newEmpresa.getProjeto(1));
		assertEquals(newProjeto2, newEmpresa.getProjeto(2));
	}
}
