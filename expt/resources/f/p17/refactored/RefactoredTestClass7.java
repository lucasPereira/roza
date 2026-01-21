import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void adicionaProjeto1() {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto1 = new Projeto("Projeto 1");
		rafael.adicionaProjeto(projeto1);
		assertTrue(rafael.temProjeto(projeto1));
	}
}
