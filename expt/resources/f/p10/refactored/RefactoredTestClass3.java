import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void testeAlterarResponsavelPelaOcorrencia() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		long criaNovaOcorrencia = new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		Funcionario f2 = new Funcionario("ze", "0234");
		new OcorrenciaService().alterarFuncionarioResponsavelOcorrencia(f1, f2, criaNovaOcorrencia);
		Assert.assertEquals(f1.getOcorrencias().size(), 0);
		Assert.assertEquals(f2.getOcorrencias().size(), 1);
	}
}
