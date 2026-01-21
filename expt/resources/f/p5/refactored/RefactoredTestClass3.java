import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void ligarProjetoAoFuncionario() {
		Empresa umaEmpresa = new Empresa("Empresa de Teste");
		Integer funcionarioEscolhidoParaOProjeto = 0;
		Integer projeto = 0;
		Funcionario umFuncionario = new Funcionario("Xisto");
		Projeto umProjeto = new Projeto("Projeto X");
		umaEmpresa.addFuncionario(umFuncionario);
		umaEmpresa.addProjeto(umProjeto);
		umaEmpresa.addFuncionarioAoProjeto(funcionarioEscolhidoParaOProjeto, projeto);
		assertEquals(umFuncionario, umProjeto.getFuncionario(0));
	}
}
