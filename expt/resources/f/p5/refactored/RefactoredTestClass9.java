import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private Funcionario umFuncionario;

	private Ocorrencia umaOcorrencia;

	@Before()
	public void setup() {
		umFuncionario = new Funcionario("Xisto");
		umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta, new Funcionario("Fabio"), Prioridade.Alta);
	}

	@Test()
	public void addOcorrenciaParaFuncionarioResolver() {
		umFuncionario.addOcorrencia(umaOcorrencia);
		assertEquals(umaOcorrencia, umFuncionario.getOcorrencias(0));
	}

	@Test()
	public void excedeuLimiteDeOcorrenciasPermitidas() {
		for (int i = 0; i < 11; i++) {
			umFuncionario.addOcorrencia(umaOcorrencia);
		}
	}

	@Test()
	public void mudarStatusOcorrencia() {
		umFuncionario.addOcorrencia(umaOcorrencia);
		umFuncionario.getOcorrencias(0).setStatus(Status.Fechada);
		assertEquals(Status.Fechada, umFuncionario.getOcorrencias(0).getStatus());
	}
}
