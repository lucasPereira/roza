import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void listaProjetos() {
		Funcionario func = new Funcionario("João");
		Projeto proj = new Projeto("Projeto x");
		func.atribuiProjeto(proj);
		List<Projeto> result = func.retornaProjetos();
		assertEquals(1, result.size());
		assertEquals(proj, result.get(0));
	}
}
