import org.junit.Test;

public class RefactoredTestClass13 {

	@Test()
	public void maximo10tarefasTest() {
		Funcionario rafael = new Funcionario("Rafael");
		Projeto projeto = new Projeto("Projeto");
		Ocorrencia ocorrenciaA = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaB = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaC = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaD = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaE = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaF = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaG = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaH = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaI = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaJ = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Ocorrencia ocorrenciaK = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		rafael.concluiOcorrencia(ocorrenciaJ);
		Ocorrencia ocorrenciaL = new Ocorrencia("Ocorrencia", Ocorrencia.Tipos.Tarefa);
		Funcionario lucas = new Funcionario("Lucas");
		ocorrenciaI.setResponsavel(lucas);
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaA, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaB, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaC, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaD, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaE, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaF, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaG, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaH, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaI, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaJ, projeto));
		assertFalse(rafael.adicionaOcorrencia(ocorrenciaK, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaK, projeto));
		assertFalse(rafael.adicionaOcorrencia(ocorrenciaL, projeto));
		assertTrue(rafael.adicionaOcorrencia(ocorrenciaL, projeto));
	}
}
