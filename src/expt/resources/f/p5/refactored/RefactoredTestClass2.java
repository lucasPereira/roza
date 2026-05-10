import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void addOcorrenciaParaFuncionarioResolver() {
		Projeto umProjeto = new Projeto("Projeto X");
		Integer funcionarioEscolhidoParaResolver = 0;
		Integer ocorrenciaAberta = 0;
		Funcionario umFuncionario = new Funcionario("Xisto");
		umProjeto.addFuncionarioAoProjeto(umFuncionario);
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta, umFuncionario, Prioridade.Alta);
		umProjeto.addOcorrencia(umaOcorrencia);
		umProjeto.addOcorrenciaAFuncionario(funcionarioEscolhidoParaResolver, ocorrenciaAberta);
		assertEquals(umaOcorrencia, umProjeto.getFuncionario(0).getOcorrencias(0));
	}
}
