import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass10 {

	private Empresa empresa;

	@Before()
	public void setup() {
		empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").novaListaDeOcorrencias(DelegatedSetups.criarListaDeNOcorrencias(10));
		for (Ocorrencia ocorrencia : empresa.getProjeto("Projeto TDD").getOcorrencias()) {
			ocorrencia.setResponsavel(empresa.getFuncionario("Pedro"));
		}
	}

	@Test()
	public void verificarCapacidadeDe10OcorrenciasParaUmUnicoFuncionario() {
		assertEquals(10, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().size());
	}

	@Test()
	public void verificarLimiteDe10OcorrenciasParaUmUnicoFuncionario() {
		empresa.getProjeto(empresa.getProjeto("Projeto TDD").getNome()).novaOcorrencia(new Ocorrencia("11", 11, Prioridade.BAIXA, "11", TipoOcorrencia.TAREFA));
		assertThrows(ExceptionResponsabilidadesDemais.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(11).setResponsavel(empresa.getFuncionario("Pedro"));
		});
	}
}
