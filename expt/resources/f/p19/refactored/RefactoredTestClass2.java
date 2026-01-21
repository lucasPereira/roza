import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Funcionario funcionario;

	@Before()
	public void setup() {
		funcionario = new Funcionario(1, "Mark");
	}

	@Test()
	public void testCriarFuncionario() {
		assertEquals("Mark", funcionario.getNome());
		assertEquals(1, funcionario.getId());
		assertEquals(0, funcionario.getQuantidadeDeOcorrencias());
		assertFalse(funcionario.possuiMaximoDeOcorrencias());
	}

	@Test()
	public void testIgualdadeDeFuncionarios() {
		assertEquals(funcionario, funcionario);
	}

	@Test()
	public void testIgualdadeDeFuncionariosDiferentesInstancias() {
		Funcionario funcionarioMark = new Funcionario(1, "Mark");
		assertEquals(funcionarioMark, funcionario);
	}
}
