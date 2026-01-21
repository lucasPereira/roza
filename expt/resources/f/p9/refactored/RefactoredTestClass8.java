import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa(cnpj, nome);
	}

	@Test()
	public void testAdicionarProjetoHotel() {
		Projeto hotel = new Projeto("Hotel");
		empresa.adicionarProjeto(hotel);
		assertEquals(1, empresa.projetos().size());
		assertTrue(empresa.projetos().contains(hotel));
	}

	@Test()
	public void testCriarEmpresa() {
		assertEquals(cnpj, empresa.cnpj());
		assertEquals(nome, empresa.nome());
		assertTrue(empresa.funcionarios().isEmpty());
		assertTrue(empresa.projetos().isEmpty());
	}
}
