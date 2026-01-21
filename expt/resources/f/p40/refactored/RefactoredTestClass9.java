import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Empresa empresa;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa("Gerenciador de Ocorrências");
		funcionario = empresa.criarFuncionario("Fabiano Manschein");
	}

	@Test()
	public void adicionarFuncionarioEmProjeto() {
		Projeto projeto = empresa.criarProjeto("TDD");
		empresa.atribuirFuncionarioParaProjeto(funcionario, projeto);
		assertThat(projeto.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projeto.getListaDeFuncionarios().size(), equalTo(1));
	}

	@Test()
	public void adicionarFuncionarioNaEmpresa() {
		assertThat(empresa.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(1));
	}

	@Test()
	public void criarFuncionarioComConstrutor() {
		funcionario = new Funcionario("Fabiano Manschein");
		assertThat(funcionario.getNome(), equalTo("Fabiano Manschein"));
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(0));
		assertThat(funcionario.getListaDeProjetos().size(), equalTo(0));
	}

	@Test()
	public void funcionarioPodeSerResponsavelPorAteDezOcorrenciasAbertas() {
		Boolean sucesso = atribuirOcorrenciasParaFuncionario(11, funcionario, new Projeto("TDD"));
		assertThat(funcionario.getListaDeOcorrencias().size(), lessThanOrEqualTo(10));
		assertThat(sucesso, equalTo(false));
	}

	@Test()
	public void verificarQuantiaCorretaDoMetodoAtribuirOcorrenciasParaFuncionario() {
		Projeto projeto = empresa.criarProjeto("TDD");
		Boolean sucesso = atribuirOcorrenciasParaFuncionario(10, funcionario, projeto);
		assertThat(funcionario.getListaDeOcorrencias().size(), equalTo(10));
		assertThat(sucesso, equalTo(true));
	}
}
