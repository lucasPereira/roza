import org.junit.Test;

public class RefactoredTestClass6 {

	@Test()
	public void verificarOResumoDeUmaOcorrencia() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).updateResumo("Resumo diferente do Delegated Setup");
		assertNotEquals("Eu fiz este trabalho muito tarde", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResumo());
		assertEquals("Resumo diferente do Delegated Setup", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResumo());
	}
}
