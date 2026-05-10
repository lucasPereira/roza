import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void addFuncionarioAoProjeto() {
		Projeto umProjeto = new Projeto("Projeto X");
		Funcionario umFuncionario = new Funcionario("Xisto");
		umProjeto.addFuncionarioAoProjeto(umFuncionario);
		assertEquals(umFuncionario, umProjeto.getFuncionario(0));
	}
}
