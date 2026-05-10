import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass4 {

	private Empresa empresa;

	private Projeto projeto;

	private Funcionario funcionario;

	private TipoOcorrencia tipo;

	private PrioridadeOcorrencia prioridade;

	@Before()
	public void setup() {
		empresa = new Empresa();
		projeto = empresa.addProjeto("Projeto 1");
		funcionario = empresa.addFuncionario("Responsavel");
		projeto.addFuncionario(funcionario);
		tipo = TipoOcorrencia.BUG;
		prioridade = PrioridadeOcorrencia.MEDIA;
	}

	@Test()
	public void criarDecimaPrimeiraOcorrenciaComMesmoResponsavel() {
		for (int i = 1; i < 11; ++i) {
			ocorrencia = empresa.criarOcorrencia("Ocorrencia " + String.valueOf(i), projeto, tipo, prioridade, funcionario);
		}
		try {
			ocorrencia = empresa.criarOcorrencia("Ocorrencia 11", projeto, tipo, prioridade, funcionario);
		} catch (Exception e) {
		} finally {
		}
		assertEquals(10, ocorrencia.id().intValue());
	}

	@Test()
	public void criarEConcluirOcorrencia() {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		empresa.concluirOcorrencia(ocorrencia);
		assertEquals(0, funcionario.numOcorrenciasAtribuidas().intValue());
		assertEquals(EstadoOcorrencia.FECHADA, ocorrencia.estado());
	}

	@Test()
	public void criarPrimeiraOcorrencia() {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		assertEquals(1, ocorrencia.id().intValue());
		assertEquals("Ocorrencia 1", ocorrencia.nome());
		assertEquals(funcionario.id(), ocorrencia.responsavel().id());
	}

	@Test()
	public void criarSegundaOcorrencia() {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		Ocorrencia ocorrencia2 = empresa.criarOcorrencia("Ocorrencia 2", projeto, tipo, prioridade, funcionario);
		assertEquals(1, ocorrencia.id().intValue());
		assertEquals(2, ocorrencia2.id().intValue());
	}
}
