import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void listaDoisProjetos() {
		Funcionario func = new Funcionario("João");
		Projeto proj = new Projeto("Projeto x");
		Projeto proj2 = new Projeto("Projeto y");
		func.atribuiProjeto(proj);
		func.atribuiProjeto(proj2);
		List<Projeto> result = func.retornaProjetos();
		assertEquals(2, result.size());
		assertEquals(proj, result.get(0));
		assertEquals(proj2, result.get(1));
	}
}
