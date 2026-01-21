import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void verificarModificacaoDePrioridadeResultaEmErroCasoOcorrenciaEstejaFechada() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).fecharOcorrencia();
		assertThrows(ExceptionOcorrenciaFechada.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(5).setPrioridade(Prioridade.BAIXA);
		});
	}
}
