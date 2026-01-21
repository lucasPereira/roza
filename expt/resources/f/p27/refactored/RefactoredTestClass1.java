import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void atribuiDoisProjetos() {
		Projeto proj = new Projeto("projeto x");
		Projeto proj2 = new Projeto("projeto y");
		Funcionario func = new Funcionario("João");
		func.atribuiProjeto(proj);
		boolean result = func.atribuiProjeto(proj2);
		assertEquals(true, result);
	}
}
