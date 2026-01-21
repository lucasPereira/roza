import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass13 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
	}

	@Test()
	public void verificarNomeProjeto() {
		assertEquals("Projeto TDD", empresa.getProjeto("Projeto TDD").getNome());
		assertEquals(null, empresa.getProjeto("Projeto B"));
	}

	@Test()
	public void verificarOResponsavelDeUmProjeto() {
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		assertEquals("Pedro", empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro")).getNome());
	}
}
