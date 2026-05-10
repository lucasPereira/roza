import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass13 {

	private Ocorrencia ocorrencia;

	@Before()
	public void setup() {
		ocorrencia = projeto.criaOcorrencia();
		ocorrencia.defineEstado(Ocorrencia.Estado.COMPLETADA);
	}

	@Test()
	public void testeDefinePrioridadeAltaAOcorrenciaCompletada() {
		assertThrows(OcorrenciaException.class, () -> {
			ocorrencia.definePrioridade(Ocorrencia.Prioridade.ALTA);
		});
	}

	@Test()
	public void testeDefineResponsavelAOcorrenciaCompletada() {
		Funcionario joao = empresa.criaFuncionario("Joao da Silva");
		assertThrows(OcorrenciaException.class, () -> {
			ocorrencia.defineResponsavel(joao);
		});
	}
}
