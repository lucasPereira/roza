import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void adicionaProjeto2() {
		Funcionario funcionario = new Funcionario("Matheus");
		Projeto projeto02 = new Projeto("Projeto02");
		funcionario.adicionaProjeto(projeto02);
		assertTrue(funcionario.listaProjeto(projeto02));
	}
}
