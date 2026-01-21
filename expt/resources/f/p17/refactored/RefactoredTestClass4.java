import org.junit.Test;

public class RefactoredTestClass4 {

	@Test()
	public void adicionaOcorrenciaAeB() {
		Projeto projeto = new Projeto("Projeto");
		ArrayList<Ocorrencia> ocorrencias = new ArrayList<Ocorrencia>();
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaA);
		ocorrencias.add(ocorrenciaA);
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia B", Ocorrencia.Tipos.Tarefa);
		projeto.adicionaOcorrencia(ocorrenciaB);
		ocorrencias.add(ocorrenciaB);
		assertTrue(projeto.getListaOcorrencias().equals(ocorrencias));
	}
}
