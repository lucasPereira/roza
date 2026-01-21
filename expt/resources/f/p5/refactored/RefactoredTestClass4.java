import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void quantidadeFuncionariosDaEmpresa() {
		Empresa umaEmpresa = new Empresa("Empresa de Teste");
		umaEmpresa.addFuncionario(new Funcionario("Xisto"));
		umaEmpresa.addFuncionario(new Funcionario("Fabio"));
		umaEmpresa.addFuncionario(new Funcionario("B"));
		assertEquals(new Integer(3), umaEmpresa.getQtdFuncionarios());
	}
}
