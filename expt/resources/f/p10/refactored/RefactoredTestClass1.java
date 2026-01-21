import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionaOcorrenciaAoFuncionario() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		Assert.assertEquals(f1.getOcorrencias().size(), 1);
	}
}
