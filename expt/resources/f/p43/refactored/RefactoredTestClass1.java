import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void ocorrenciaComChaveRepetida() {
		Empresa empresa = new Empresa("Empresa", 12345);
		criaFuncionarioJoao();
		criaProjetoDesenvolvimentoSite();
		Ocorrencia ocorrencia1 = new Ocorrencia(1, joao, Prioridade.ALTA, TipoOcorrencia.TAREFA, true);
		Ocorrencia ocorrencia2 = new Ocorrencia(2, joao, Prioridade.MEDIA, TipoOcorrencia.BUG, true);
		Ocorrencia ocorrencia3 = new Ocorrencia(1, joao, Prioridade.BAIXA, TipoOcorrencia.MELHORIA, true);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia1);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia2);
		desenvolvimentoSite.inserirOcorrencia(ocorrencia3);
	}
}
