import org.junit.Test;

public class RefactoredTestClass8 {

	@Test()
	public void verificarRemocaoDeUmaOcorrenciaDasResponsabilidadesDoFuncionario() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.BUG));
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("2", 2, Prioridade.BAIXA, "2", TipoOcorrencia.BUG));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").getOcorrencia(2).setResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).removerResponsavel();
		assertEquals(1, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().size());
		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(1).getResponsavel());
		assertEquals(2, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().get(0).getChave());
	}
}
