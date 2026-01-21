import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass11 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = DelegatedSetups.novaEmpresaComDoisFuncionariosEUmProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
	}

	@Test()
	public void verificarErroAoConfigurarDoisResponsaveisAUmaUnicaOcorrencia() {
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.TAREFA));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));
		assertThrows(ExceptionJaTemResponsavel.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Alexandre"));
		});
	}

	@Test()
	public void verificarRemocaoDeFuncionarioDeUmProjeto() {
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Alexandre"));
		empresa.getProjeto("Projeto TDD").removerResponsavel(empresa.getFuncionario("Pedro"));
		assertEquals(null, empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro")));
		assertEquals(empresa.getFuncionario("Alexandre"), empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Alexandre")));
	}
}
