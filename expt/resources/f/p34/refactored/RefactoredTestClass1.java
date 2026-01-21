import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarDoisFuncionarios() {
		empresaBrasil.contratarFuncionario("Paulo");
		Funcionario funcionarioPaulo = empresaBrasil.getFuncionario(1);
		empresaBrasil.adicionarProjeto("IMW");
		Projeto projetoImw = empresaBrasil.getProjeto(0);
		projetoImw.adicionarFuncionario(funcionarioLuiz);
		projetoImw.adicionarFuncionario(funcionarioPaulo);
		assertEquals(2, projetoImw.getNumeroDeFuncionarios());
	}
}
