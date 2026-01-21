import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void verificarAsOcorrenciasPelasQuaisUmFuncionarioEhResponsavel() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.TAREFA));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("2", 2, Prioridade.MEDIA, "2", TipoOcorrencia.BUG));
		empresa.getProjeto("Projeto TDD").getOcorrencia(2).setResponsavel(empresa.getFuncionario("Pedro"));
		assertEquals(2, empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro")).getOcorrenciasQueEhResponsavel().size());
	}
}
