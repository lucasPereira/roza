import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass21 {

	private Funcionario rafael;

	private Projeto projeto;

	private Ocorrencia ocorrenciaA;

	@Before()
	public void setup() {
		rafael = new Funcionario("Rafael");
		projeto = new Projeto("Projeto");
		ocorrenciaA = new Ocorrencia("Ocorrencia A", Ocorrencia.Tipos.Tarefa);
		rafael.adicionaOcorrencia(ocorrenciaA, projeto);
	}

	@Test()
	public void adicionaOcorrenciaAaoProjeto() {
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}

	@Test()
	public void concluiOcorrenciaTest() {
		rafael.concluiOcorrencia(ocorrenciaA);
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
		assertFalse(rafael.temOcorrencia(ocorrenciaA));
		assertFalse(projeto.temOcorrencia(ocorrenciaA));
	}

	@Test()
	public void mudaFuncionarioOcorrenciaA() {
		Funcionario lucas = rafael = new Funcionario("Lucas");
		ocorrenciaA.setResponsavel(lucas);
		assertTrue(rafael.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
		assertTrue(lucas.temOcorrencia(ocorrenciaA));
		assertTrue(projeto.temOcorrencia(ocorrenciaA));
	}
}
