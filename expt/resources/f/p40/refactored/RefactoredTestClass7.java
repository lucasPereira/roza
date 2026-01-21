import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass7 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Gerenciador de Ocorrências");
	}

	@Test()
	public void adicionarProjetoNaEmpresa() {
		Projeto novoProjeto = empresa.criarProjeto("TDD");
		assertThat(empresa.getListaDeProjetos(), hasItem(novoProjeto));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
	}

	@Test()
	public void criarEmpresa() {
		assertThat(empresa.getNome(), equalTo("Gerenciador de Ocorrências"));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(0));
	}
}
