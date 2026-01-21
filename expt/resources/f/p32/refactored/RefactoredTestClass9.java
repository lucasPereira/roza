import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void verificarTipoDaOcorrencia() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setTipo(TipoOcorrencia.BUG);
		assertNotEquals(TipoOcorrencia.TAREFA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getTipo());
		assertEquals(TipoOcorrencia.BUG, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getTipo());
	}
}
