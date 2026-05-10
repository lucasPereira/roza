import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void verificarModificacaoDePrioridadeDeOcorrencia() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setPrioridade(Prioridade.ALTA);
		assertNotEquals(Prioridade.MEDIA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getPrioridade());
		assertEquals(Prioridade.ALTA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getPrioridade());
	}
}
