import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarDoisFuncionarios() {
		empresa.contratarFuncionario("mariana");
		Funcionario mariana = empresa.pegarFuncionario(1);
		empresa.adicionarProjeto("IMW");
		Projeto projetoImw = empresa.pegarProjeto(0);
		projetoImw.adicionarFuncionario(funcionario);
		projetoImw.adicionarFuncionario(mariana);
		assertEquals(2, projetoImw.pegarNumeroDeFuncionarios());
	}
}
