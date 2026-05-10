import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void ocorrenciaSempreCriadaNaoCompletada() {
		this.ocorrencia = new Ocorrencia();
		Ocorrencia ocorrencia = new Ocorrencia();
		Ocorrencia ocorrencia2 = new Ocorrencia(0L, "", new Funcionario(), TipoOcorrencia.BUG, Prioridade.MEDIA);
		assertFalse(ocorrencia.isCompletada());
		assertFalse(ocorrencia2.isCompletada());
	}
}
