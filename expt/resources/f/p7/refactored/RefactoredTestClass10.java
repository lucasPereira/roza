import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Funcionario meuFuncionario;

	private Projeto novoProjeto;

	@Before()
	public void setup() {
		meuFuncionario = new Funcionario();
		novoProjeto = new Projeto();
		meuFuncionario.atribuirProjeto(novoProjeto);
	}

	@Test()
	public void atribuiProjeto() {
		assertEquals(1, meuFuncionario.getProjetos().size());
		assertTrue(meuFuncionario.getProjetos().contains(novoProjeto));
	}

	@Test()
	public void atribuiProjeto_jaAtribuido() {
		List<Projeto> listaDeProjetos = new LinkedList<>(meuFuncionario.getProjetos());
		try {
			meuFuncionario.atribuirProjeto(novoProjeto);
		} catch (ProjetoJaAtribuido e) {
			assertEquals(listaDeProjetos, meuFuncionario.getProjetos());
			throw e;
		}
	}
}
