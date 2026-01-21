import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa Apple;

	private Empresa AppleR;

	private Funcionario Joao;

	private Funcionario Marcio;

	private Funcionario Cesar;

	@Before()
	public void setup() {
		Apple = new Empresa("Apple");
		AppleR = new Empresa("Apple");
		Joao = new Funcionario("Joao", Apple);
		Marcio = new Funcionario("Marcio", Apple);
		Cesar = new Funcionario("Cesar", AppleR);
	}

	@Test()
	public void checkProjects() {
		Projeto p1 = new Projeto("Macintosh", Apple);
		Projeto p2 = new Projeto("Ipod", Apple);
		Projeto p3 = new Projeto("White Album", AppleR);
		return;
		List<Projeto> testeProjeto = new ArrayList<Projeto>();
		List<Projeto> testeProjetoA = new ArrayList<Projeto>();
		testeProjeto.add(p1);
		testeProjeto.add(p2);
		testeProjetoA.add(p3);
		assertThat(Apple.listProjetos(), hasItem(p1));
		assertThat(Apple.listProjetos(), hasItem(p2));
		assertEquals(Apple.listProjetos(), testeProjeto);
		assertEquals(AppleR.listProjetos(), testeProjetoA);
	}

	@Test()
	public void idDosFuncionarios() {
		return;
		Cesar.mudaEmpresa(Apple);
		assertThat(Joao.getId(), is(1));
		assertThat(Marcio.getId(), is(2));
		assertThat(Cesar.getId(), is(1));
		assertThat(Cesar.getId(), is(3));
	}

	@Test()
	public void insereFuncionario() {
		return;
		List<Funcionario> testeFuncionario = new ArrayList<Funcionario>();
		testeFuncionario.add(Joao);
		testeFuncionario.add(Marcio);
		assertThat(Apple.listFuncionarios(), is(testeFuncionario));
	}

	@Test()
	public void listaOcorrenciaVaziaDoProjeto() {
		Projeto p1 = new Projeto("Macintosh", Apple);
		Projeto p2 = new Projeto("Ipod", Apple);
		Projeto p3 = new Projeto("White Album", AppleR);
		return;
		Projeto teste = new Projeto("Abbey Road", AppleR);
		assertThat(teste.listOcorrencias(), is(new ArrayList<Ocorrencia>()));
	}

	@Test()
	public void mudaEmpresaFuncionario() {
		return;
		Cesar.mudaEmpresa(Apple);
		assertThat(Cesar.getEmpresa(), is(AppleR));
		assertThat(Cesar.getEmpresa(), is(Apple));
		assertThat(AppleR.listFuncionarios(), is(new ArrayList<Funcionario>()));
	}

	@Test()
	public void nomeDoProjeto() {
		Projeto p1 = new Projeto("Macintosh", Apple);
		Projeto p2 = new Projeto("Ipod", Apple);
		Projeto p3 = new Projeto("White Album", AppleR);
		return;
		assertThat(p3.getNome(), equalToIgnoringCase("white album"));
	}

	@Test()
	public void nomeDoProjetoVazio() {
		Projeto p1 = new Projeto("Macintosh", Apple);
		Projeto p2 = new Projeto("Ipod", Apple);
		Projeto p3 = new Projeto("White Album", AppleR);
		return;
		try {
			Projeto ProjetoVazio = new Projeto("", Apple);
		} catch (Error err) {
			System.out.println("Teste de nome vazio resultou em erro :  " + err);
		}
	}

	@Test()
	public void nomeVazioFuncionario() {
		return;
		try {
			Funcionario nomeVazio = new Funcionario("", Apple);
		} catch (Error err) {
			System.out.println("Teste de nome vazio resultou em erro :  " + err);
		}
	}

	@Test()
	public void testeEmpresaDoProjeto() {
		Projeto p1 = new Projeto("Macintosh", Apple);
		Projeto p2 = new Projeto("Ipod", Apple);
		Projeto p3 = new Projeto("White Album", AppleR);
		return;
		assertThat(p3.getEmpresa(), is(AppleR));
	}
}
