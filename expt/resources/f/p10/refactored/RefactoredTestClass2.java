import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void pegarFuncionarioPorCpf() {
		Projeto projeto = new Projeto();
		Funcionario f1 = new Funcionario("Chris", "0123");
		new ProjetoService().addFuncionarioNoProjeto(projeto, f1);
		Funcionario funcionarioGetByCPF = new FuncionarioService().getFuncionarioByCpf("0123", projeto);
		Assert.assertEquals(f1, funcionarioGetByCPF);
	}
}
