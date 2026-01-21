import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Empresa umaEmpresa;

	@Before()
	public void setup() {
		umaEmpresa = new Empresa("Empresa de Teste");
		umaEmpresa.addProjeto(new Projeto("Projeto Zeta"));
	}

	@Test()
	public void adicionarProjetoALista() {
		assertEquals("Projeto Zeta", umaEmpresa.getProjeto(0).getNomeProjeto());
	}

	@Test()
	public void quantidadeProjetosDaEmpresa() {
		umaEmpresa.addProjeto(new Projeto("Projeto X"));
		umaEmpresa.addProjeto(new Projeto("Projeto Y"));
		assertEquals(new Integer(3), umaEmpresa.getQtdProjetos());
	}
}
