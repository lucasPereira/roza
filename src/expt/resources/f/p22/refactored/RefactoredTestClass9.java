import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Funcionario funcionario;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Matheus");
	}

	@Test()
	public void adicionaProjeto2() {
		Projeto projeto02 = new Projeto("Projeto02");
		funcionario.adicionaProjeto(projeto02);
		assertTrue(funcionario.listaProjeto(projeto02));
	}

	@Test()
	public void criafuncionario() {
		assertEquals("Matheus", funcionario.Nome());
	}
}
