import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private Empresa minhaEmpresa;

	private Funcionario meuFuncionario;

	@Before()
	public void setup() {
		minhaEmpresa = new Empresa();
		meuFuncionario = new Funcionario();
		minhaEmpresa.cadastrarFuncionario(meuFuncionario);
	}

	@Test()
	public void cadastrarFuncionario() {
		assertEquals(1, minhaEmpresa.getFuncionarios().size());
		assertTrue(minhaEmpresa.getFuncionarios().contains(meuFuncionario));
	}

	@Test()
	public void cadastrarFuncionario_jaCadastrado() {
		List<Funcionario> listaDeFuncionarios = new LinkedList<>(minhaEmpresa.getFuncionarios());
		try {
			minhaEmpresa.cadastrarFuncionario(meuFuncionario);
		} catch (FuncionarioJaCadastrado e) {
			assertEquals(listaDeFuncionarios, minhaEmpresa.getFuncionarios());
			throw e;
		}
	}
}
