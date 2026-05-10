import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = new Empresa("Empresa Spotify");
		empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));
	}

	@Test()
	public void buscarProjetoTest() {
		Projeto projeto = empresa.buscarProjeto("Projeto Primmer");
		assertThat(projeto.obterNome(), equalTo("Projeto Primmer"));
	}

	@Test()
	public void cadastrarProjetoTest() {
		assertThat(empresa.getListaDeProjetos().get(0).obterNome(), equalTo("Projeto Primmer"));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
	}

	@Test()
	public void empresaComVariosProjetosTest() {
		empresa.cadastrarProjeto(new Projeto("Projeto Plus"));
		assertThat(2, equalTo(empresa.getListaDeProjetos().size()));
		assertThat(pegarNomeDosProjetos(), hasItems("Projeto Primmer", "Projeto Plus"));
	}

	@Test()
	public void pegarNomeProjetoTest() {
		assertThat(empresa.getListaDeProjetos().get(0).obterNome(), hasToString("Projeto Primmer"));
	}
}
