import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
	}

	@Test()
	public void empresaZeroFuncionarios() {
		List<Funcionario> funcionarios = empresa.getFuncionarios();
		assertEquals(0, funcionarios.size());
	}

	@Test()
	public void empresaZeroProjetos() {
		List<Projeto> projetos = empresa.getProjetos();
		assertEquals(0, projetos.size());
	}
}
