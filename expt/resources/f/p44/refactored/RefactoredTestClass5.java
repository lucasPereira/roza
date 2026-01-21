import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass5 {

	private Empresa empresa;

	private Funcionario funcionario;

	@Before()
	public void setup() {
		empresa = new Empresa("Google");
		funcionario = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(funcionario);
	}

	@Test()
	public void funcionarioDezOcorrencia() {
		Projeto projeto = new Projeto("Pixel 5");
		projeto.addFuncionario(funcionario);
		empresa.addProj(projeto);
		int numeroOcorrencias = 10;
		new TestsHelper().makeListaOcorrencias(numeroOcorrencias, funcionario, projeto);
		List<Ocorrencia> ocorrencias = funcionario.getOcorrenciasAbertas();
		assertEquals(10, ocorrencias.size());
	}

	@Test()
	public void funcionarioOnzeOcorrencia() {
		Projeto projeto = new Projeto("Pixel 5");
		int numeroOcorrencias = 11;
		new TestsHelper().makeListaOcorrencias(numeroOcorrencias, funcionario, projeto);
	}

	@Test()
	public void funcionarioZeroOcorrencias() {
		List<Ocorrencia> ocorrencias = funcionario.getOcorrenciasAbertas();
		assertEquals(0, ocorrencias.size());
	}

	@Test()
	public void funcionarioZeroProjetos() {
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		assertEquals(0, projetos.size());
	}
}
