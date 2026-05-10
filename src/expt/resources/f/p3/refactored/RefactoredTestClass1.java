import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projeto = new Projeto("NovoProjeto");
		funcionario = new Funcionario("Joao");
	}

	@Test()
	public void adicionarFuncionario() {
		empresa.cadastrarFuncionario(funcionario.getName());
		assertEquals(funcionario.getName(), empresa.getFuncionarios().get(0).getName());
	}

	@Test()
	public void criarFuncionario() {
		assertEquals(funcionario.getName(), "Joao");
	}
}
