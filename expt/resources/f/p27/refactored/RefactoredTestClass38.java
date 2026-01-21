import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass38 {

	private Projeto proj;

	private Funcionario func;

	@Before()
	public void setup() {
		proj = new Projeto("projeto x");
		func = new Funcionario("João");
	}

	@Test()
	public void atribuiProjeto() {
		boolean result = func.atribuiProjeto(proj);
		assertEquals(true, result);
	}

	@Test()
	public void atribuiProjetoDuasVezes() {
		func.atribuiProjeto(proj);
		boolean result = func.atribuiProjeto(proj);
		assertEquals(false, result);
	}
}
