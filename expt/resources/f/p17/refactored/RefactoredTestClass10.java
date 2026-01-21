import org.junit.Test;

public class RefactoredTestClass10 {

	@Test()
	public void adicionaRafael() {
		Empresa hexagon = new Empresa("Hexagon");
		Funcionario rafael = new Funcionario("Rafael");
		hexagon.adicionaFuncionario(rafael);
		assertTrue(hexagon.temFuncionario(rafael));
	}
}
