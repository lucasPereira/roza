import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Empresa umaEmpresa;

	@Before()
	public void setup() {
		umaEmpresa = new Empresa("Empresa de Teste");
	}

	@Test()
	public void adicionarFuncionarioALista() {
		umaEmpresa.addFuncionario(new Funcionario("Xisto"));
		assertEquals("Xisto", umaEmpresa.getFuncionarios(0).getNome());
	}

	@Test()
	public void retornaNomeEmpresa() {
		assertEquals("Empresa de Teste", umaEmpresa.getNomeEmpresa());
	}
}
