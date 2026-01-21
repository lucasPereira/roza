import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass2 {

	private Funcionario funcionario;

	@Before()
	public void setup() {
		funcionario = new Funcionario("Leonardo Passig Horstmann", new Date(1996, 8, 1));
	}

	@Test()
	public void adicionaFuncionarioAEmpresa() {
		Empresa empresa = new Empresa("nome");
		empresa.adicionaFuncionario(funcionario);
		assertEquals(1, empresa.numFuncionarios().intValue());
	}

	@Test()
	public void testCriaFuncionario() {
		assertEquals("Leonardo Passig Horstmann", funcionario.nome());
		assertEquals(new Date(1996, 8, 1), funcionario.data_nascimento());
	}
}
