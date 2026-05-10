import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass3 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario bob;

	private Ocorrencia somaBugada;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projeto = new Projeto();
		empresa.addProjeto(projeto);
		bob = new Funcionario();
		somaBugada = exemploOcorrencia(bob);
	}

	@Test()
	public void adicionarOcorrencia() {
		empresa.addFuncionario(bob);
		projeto.addOcorrencia(somaBugada);
		assertEquals(1, projeto.numOcorrencias());
	}

	@Test()
	public void adicionarOcorrenciaDeFuncionarioForaDaEmpresa() {
		projeto.addOcorrencia(somaBugada);
	}
}
