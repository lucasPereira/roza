import org.junit.Test;

public class RefactoredTestClass2 {

	@Test()
	public void deveAlterarResponsavel() {
		this.ocorrencia = new Ocorrencia();
		Funcionario funcionario = new Funcionario();
		this.ocorrencia.alterarResponsavel(funcionario);
		assertEquals(funcionario, this.ocorrencia.getResponsavel());
	}
}
