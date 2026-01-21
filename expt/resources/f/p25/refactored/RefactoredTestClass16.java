import org.junit.Test;

public class RefactoredTestClass16 {

	@Test()
	public void trocaResponsavelOcorrenciaFechada() {
		Funcionario novoFuncionario = empresa.contrataFuncionario();
		funcionario.terminaOcorrencia(ocorrencia);
		assertThrows(MudarOcorrenciaFechadaException.class, () -> {
			ocorrencia.trocaFuncionarioResponsavel(novoFuncionario);
		}, "Uma ocorrencia fechada n�o pode ser modificada");
	}
}
