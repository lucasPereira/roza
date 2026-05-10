import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass12 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("Socorram-me!", 1, Prioridade.ALTA, "Subi no �nibus em Marrocos", TipoOcorrencia.TAREFA));
	}

	@Test()
	public void verificarErroAoCriarOcorrenciasEmUmMesmoProjetoComAMesmaChave() {
		assertThrows(ExceptionMesmaChave.class, () -> {
			empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("socorraM me", 1, Prioridade.ALTA, "subin� on ibuS !em-marrocoS", TipoOcorrencia.BUG));
		});
	}

	@Test()
	public void verificarMultiplasOcorrenciasEmUmProjeto() {
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("socorraM me", 2, Prioridade.ALTA, "subin� on ibuS !em-marrocoS", TipoOcorrencia.BUG));
		assertEquals("Socorram-me!", empresa.getProjeto("Projeto TDD").getOcorrencia(1).getNome());
		assertEquals("socorraM me", empresa.getProjeto("Projeto TDD").getOcorrencia(2).getNome());
	}
}
