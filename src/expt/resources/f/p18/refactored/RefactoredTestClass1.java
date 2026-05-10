import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass1 {

	private Empresa Apple;

	private Empresa AppleR;

	private Empresa Angeloni;

	private Funcionario Joao;

	private Funcionario Marcio;

	private Funcionario Cesar;

	@Before()
	public void setup() {
		Apple = new Empresa("Apple");
		AppleR = new Empresa("Apple");
		Angeloni = new Empresa("Angeloni");
		Joao = new Funcionario("Joao", Apple);
		Marcio = new Funcionario("Marcio", Apple);
		Cesar = new Funcionario("Cesar", AppleR);
		return;
	}

	@Test()
	public void listaFuncionarioVaziaDaEmpresa() {
		assertThat(Angeloni.listFuncionarios(), is(new ArrayList<Funcionario>()));
	}

	@Test()
	public void listaProjetoVaziaDaEmpresa() {
		assertThat(Angeloni.listProjetos(), is(new ArrayList<Projeto>()));
	}

	@Test()
	public void nomeDeEmpresa() {
		assertThat(Apple.nome(), equalToIgnoringCase("Apple"));
		assertThat(AppleR.nome(), equalToIgnoringCase("apple"));
	}

	@Test()
	public void nomeVazioEmpresa() {
		try {
			Empresa EmpresaVazia = new Empresa("");
		} catch (Error err) {
			System.out.println("Teste de nome vazio resultou em erro :  " + err);
		}
	}
}
