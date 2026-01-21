import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass13 {

	private Funcionario meuFuncionario;

	private Ocorrencia novaOcorrencia;

	@Before()
	public void setup() {
		meuFuncionario = new Funcionario();
		novaOcorrencia = new Ocorrencia("resumo", new Funcionario(), Tipo.TAREFA);
		meuFuncionario.atribuirOcorrencia(novaOcorrencia);
	}

	@Test()
	public void atribuiOcorrencia() {
		assertEquals(1, meuFuncionario.getOcorrenciasAbertas().size());
		assertTrue(meuFuncionario.getOcorrenciasAbertas().contains(novaOcorrencia));
		assertEquals(meuFuncionario, novaOcorrencia.getFuncionarioResponsavel());
	}

	@Test()
	public void atribuiOcorrencia_jaCompletada() {
		meuFuncionario.terminaOcorrencia(novaOcorrencia);
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		} catch (OcorrenciaJaCompletada e) {
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			throw e;
		}
	}

	@Test()
	public void atribuiOcorrencia_jaLheFoiAtribuida() {
		List<Ocorrencia> listaDeOcorrencias = new LinkedList<>(meuFuncionario.getOcorrenciasAbertas());
		try {
			meuFuncionario.atribuirOcorrencia(novaOcorrencia);
		} catch (FuncionarioJaPossuiTalOcorrencia e) {
			assertEquals(listaDeOcorrencias, meuFuncionario.getOcorrenciasAbertas());
			assertEquals(meuFuncionario, novaOcorrencia.getFuncionarioResponsavel());
			throw e;
		}
	}
}
