import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass47 {

	private Projeto proj;

	private Projeto proj;

	private Funcionario func;

	@Before()
	public void setup() {
		proj = new Projeto("projeto x");
		proj = new Projeto("projeto x");
		func = new Funcionario("João");
	}

	@Test()
	public void listaDoisFuncionarios() {
		Funcionario func2 = new Funcionario("Maria");
		proj.atribuiFuncionario(func);
		proj.atribuiFuncionario(func2);
		List<Funcionario> result = proj.retornaFuncionarios();
		assertEquals(2, result.size());
		assertEquals(func, result.get(0));
		assertEquals(func2, result.get(1));
	}

	@Test()
	public void listaFuncionarios() {
		proj.atribuiFuncionario(func);
		List<Funcionario> result = proj.retornaFuncionarios();
		assertEquals(1, result.size());
		assertEquals(func, result.get(0));
	}

	@Test()
	public void testeAtribuiDoisFuncionarios() {
		Funcionario func2 = new Funcionario("Maria");
		proj.atribuiFuncionario(func);
		boolean result = proj.atribuiFuncionario(func2);
		assertEquals(true, result);
	}

	@Test()
	public void testeAtribuiFuncionario() {
		boolean result = proj.atribuiFuncionario(func);
		assertEquals(true, result);
	}

	@Test()
	public void testeAtribuiFuncionarioDuasVezes() {
		proj.atribuiFuncionario(func);
		boolean result = proj.atribuiFuncionario(func);
		assertEquals(false, result);
	}
}
