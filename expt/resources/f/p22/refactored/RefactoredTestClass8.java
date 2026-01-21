import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Funcionario funcionario;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Matheus");
	}

	@Test()
	public void adicionaProjeto1() {
		Projeto projeto01 = new Projeto("Projeto01");
		funcionario.adicionaProjeto(projeto01);
		assertTrue(funcionario.listaProjeto(projeto01));
	}

	@Test()
	public void criafuncionario() {
		assertEquals("Matheus", funcionario.Nome());
	}
}
