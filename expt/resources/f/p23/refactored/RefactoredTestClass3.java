import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("nome");
	}

	@Test()
	public void criaEmpresa() {
		assertEquals("nome", empresa.nome());
	}

	@Test()
	public void criaProjetoNaEmpresa() {
		Projeto p = empresa.criaProjeto("meu_proj");
		assertEquals("meu_proj", p.nome());
		assertEquals(1, empresa.numeroDeProjetos());
	}
}
