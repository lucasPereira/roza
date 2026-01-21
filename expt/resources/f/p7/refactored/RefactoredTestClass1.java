import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void atribuiProjeto_2() {
		Funcionario meuFuncionario = new Funcionario();
		Projeto primeiroProjeto = new Projeto();
		meuFuncionario.atribuirProjeto(primeiroProjeto);
		Projeto segundoProjeto = new Projeto();
		meuFuncionario.atribuirProjeto(segundoProjeto);
		assertEquals(2, meuFuncionario.getProjetos().size());
		assertTrue(meuFuncionario.getProjetos().contains(primeiroProjeto));
		assertTrue(meuFuncionario.getProjetos().contains(segundoProjeto));
	}
}
