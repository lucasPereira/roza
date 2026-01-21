import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass40 {

	private Empresa emp;

	private String nomeProj;

	private Projeto proj;

	@Before()
	public void setup() {
		emp = new Empresa("Empresa x");
		nomeProj = "projeto x";
		proj = emp.criaProjeto(nomeProj);
	}

	@Test()
	public void testeAtribuiProjetoFuncionarioNull() {
		boolean result = emp.atribuiProjetoFuncionario(null, proj);
		assertEquals(false, result);
	}

	@Test()
	public void testeCriarProjeto() {
		assertEquals(proj.getNome(), nomeProj);
	}
}
