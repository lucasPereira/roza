import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Funcionario funcionario;

	private Empresa empresa;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Paulo");
		empresa = new Empresa("Massashin");
	}

	@Test()
	public void criaFuncionarioAnonimo() {
		Funcionario funcionarioAnonimo = new Funcionario("");
		assertEquals("", funcionarioAnonimo.geraNomeFun());
	}

	@Test()
	public void criaFuncionarioNormal() {
		String funcionarioComNome = "Paulo";
		assertEquals(funcionarioComNome, funcionario.geraNomeFun());
	}
}
