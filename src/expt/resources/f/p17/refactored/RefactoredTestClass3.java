import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void adicionaOcorrenciaAaoProjeto1eBaoProjeto2() {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto1 = new Projeto("Projeto");
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto1);
		ocorrencias.add(ocorrenciaA);
		Projeto projeto2 = new Projeto("Projeto");
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaB, projeto2);
		ocorrencias.add(ocorrenciaB);
		assertTrue(rafael.getListaOcorrencias().equals(ocorrencias));
		assertFalse(projeto1.getListaOcorrencias().equals(ocorrencias));
		assertFalse(projeto2.getListaOcorrencias().equals(ocorrencias));
	}
}
