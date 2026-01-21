import org.junit.Test;

public class RefactoredTestClass15 {

	@Test()
	public void trocaResponsavelOcorrenciaAberta() {
		Funcionario novoFuncionario = empresa.contrataFuncionario();
		ocorrencia.trocaFuncionarioResponsavel(novoFuncionario);
		assertEquals(novoFuncionario, ocorrencia.getFuncionarioResponsavel());
	}
}
