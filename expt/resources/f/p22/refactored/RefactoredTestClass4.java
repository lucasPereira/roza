import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void adicionaProjeto1() {
		Funcionario funcionario = new Funcionario("Matheus");
		Projeto projeto01 = new Projeto("Projeto01");
		funcionario.adicionaProjeto(projeto01);
		assertTrue(funcionario.listaProjeto(projeto01));
	}
}
