import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void adicionaOcorrenciaAeBaoProjeto() {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto = new Projeto("Projeto");
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
		ocorrencias.add(ocorrenciaA);
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto);
		ocorrencias.add(ocorrenciaB);
		assertTrue(rafael.getListaOcorrencias().equals(ocorrencias));
		assertTrue(projeto.getListaOcorrencias().equals(ocorrencias));
	}
}
