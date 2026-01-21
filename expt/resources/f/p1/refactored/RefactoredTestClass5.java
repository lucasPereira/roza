import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	@Before()
	public void setup() {
		this.empresa = new Empresa();
		this.joao = new Funcionario();
		this.projeto = new Projeto();
	}

	@Test()
	public void adicionar2FonctionariosEmpresa() {
		empresa.addFuncionario(joao);
		Funcionario mario = new Funcionario();
		empresa.addFuncionario(mario);
		assertEquals(joao, empresa.getFonctionario(0));
		assertEquals(mario, empresa.getFonctionario(1));
	}

	@Test()
	public void adicionarFuncionariosEmpresa() {
		empresa.addFuncionario(joao);
		assertEquals(joao, empresa.getFonctionario(0));
	}

	@Test()
	public void adicionarProjetoNaEmpresa() {
		empresa.addProjeto(projeto);
		assertEquals(projeto, empresa.getProjeto(0));
	}

	@Test()
	public void criarEmpresa() {
		assertNotEquals(null, new Empresa());
	}

	@Test()
	public void criarFuncionario() {
		Funcionario joao = new Funcionario();
		assertNotEquals(null, joao);
	}

	@Test()
	public void criarOcurrencia() {
		Funcionario fonctionario = new Funcionario();
		Ocorrencia ocurencia = new Ocorrencia(Prioridades.ALTA, "resumo", fonctionario);
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());
	}

	@Test()
	public void criarProjeto() {
		Projeto projeto = new Projeto();
		assertNotEquals(null, projeto);
	}
}
