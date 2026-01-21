import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionaLucasERafael() {
		Empresa hexagon = new Empresa("Hexagon");
		ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
		Funcionario rafael = new Funcionario("Rafael");
		hexagon.adicionaFuncionario(rafael);
		funcionarios.add(rafael);
		Funcionario lucas = new Funcionario("Lucas");
		hexagon.adicionaFuncionario(lucas);
		funcionarios.add(lucas);
		assertTrue(hexagon.getListaFuncionarios().equals(funcionarios));
	}
}
