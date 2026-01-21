import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	private Funcionario func1;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		func1 = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(func1);
	}

	@Test()
	public void adicionarFuncionariosIdIguais() {
		Funcionario func2 = new Funcionario("Patricia Vilain", 0);
		empresa.addFunc(func2);
	}

	@Test()
	public void empresaDoisFuncionario() {
		Funcionario func2 = new Funcionario("Patricia Vilain", 1);
		empresa.addFunc(func2);
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		int idFunc1 = empresa.getFuncionarios().get(0).getId();
		int idFunc2 = empresa.getFuncionarios().get(1).getId();
		assertEquals(2, funcionarios.size());
		assertEquals(0, idFunc1);
		assertEquals(1, idFunc2);
	}

	@Test()
	public void empresaUmFuncionario() {
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		int idFunc1 = empresa.getFuncionarios().get(0).getId();
		assertEquals(1, funcionarios.size());
		assertEquals(0, idFunc1);
	}
}
