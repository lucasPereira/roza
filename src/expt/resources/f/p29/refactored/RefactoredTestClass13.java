import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass13 {

	private Funcionario stefanoFuncionario;

	private Funcionario stefanoFuncionario2;

	@Before()
	public void setup() {
		stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		stefanoFuncionario2 = new Funcionario(newEmpresa, "nome2", 2);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addFuncionario(stefanoFuncionario2);
	}

	@Test()
	public void adicionarDoisFuncionarios() {
		assertEquals(stefanoFuncionario, newEmpresa.getFuncionario(1));
		assertEquals(stefanoFuncionario2, newEmpresa.getFuncionario(2));
	}

	@Test()
	public void checarNomeDeFuncionario() {
		assertEquals("nome", newEmpresa.getFuncionario(1).getNome());
		assertEquals("nome2", newEmpresa.getFuncionario(2).getNome());
	}
}
