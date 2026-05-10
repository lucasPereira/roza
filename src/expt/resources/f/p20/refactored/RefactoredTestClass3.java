import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa 1");
	}

	@Test()
	public void adicionaFuncionario() {
		Funcionario joao = new Funcionario("João B. da Rosa");
		empresa.adicionaFuncionario(joao);
		assertEquals(joao.getNome(), empresa.getFuncionario("João B. da Rosa").getNome());
		assertEquals(null, empresa.getFuncionario("aaa"));
	}

	@Test()
	public void criaEmpresa() {
		assertEquals("Empresa 1", empresa.getNome());
	}

	@Test()
	public void criaJoaoConfereNome() {
		Funcionario joao = new Funcionario("João B. da Rosa");
		assertEquals("João B. da Rosa", joao.getNome());
	}

	@Test()
	public void criaProjeto() {
		assertTrue(empresa.adicionaProjeto("Projeto 1"));
	}
}
