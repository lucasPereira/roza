import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void checarIDDeFuncionario() {
		Funcionario stefanoFuncionario = new Funcionario(newEmpresa, "Stefano Bergamini Poletto", 1);
		Funcionario joaoFuncionario = new Funcionario(newEmpresa, "Joao Santana", 2);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addFuncionario(joaoFuncionario);
		assertEquals(1, newEmpresa.getFuncionario(1).getID());
		assertEquals(2, newEmpresa.getFuncionario(2).getID());
	}
}
