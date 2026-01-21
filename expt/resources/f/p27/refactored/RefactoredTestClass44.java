import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass44 {

	private Empresa emp;

	private String nome;

	@Before()
	public void setup() {
		emp = new Empresa("Empresa x");
		nome = "Empresa x";
	}

	@Test()
	public void testeCriaEmpresa() {
		Empresa emp1 = new Empresa(nome);
		String result = emp1.getNome();
		assertEquals(nome, result);
	}

	@Test()
	public void testeCriaEmpresaSemFuncionario() {
		Empresa emp = new Empresa(nome);
		List<Funcionario> result = emp.retornaFuncionarios();
		assertNotEquals(null, result);
		assertEquals(0, result.size());
	}

	@Test()
	public void testeCriaEmpresaSemProjeto() {
		Empresa emp = new Empresa(nome);
		List<Projeto> result = emp.retornaProjetos();
		assertNotEquals(null, result);
		assertEquals(0, result.size());
	}
}
